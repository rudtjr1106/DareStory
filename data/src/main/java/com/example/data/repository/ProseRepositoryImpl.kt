package com.example.data.repository

import com.example.data.DataUserInfo
import com.example.data.EndPoints
import com.example.data.dao.RecentSearchProseDao
import com.example.data.entitiy.RecentSearchProseEntity
import com.example.domain.model.enums.SearchType
import com.example.domain.model.vo.UpdateCommentVo
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.SearchVo
import com.example.domain.repository.ProseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProseRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchProseDao,
) : ProseRepository {

    companion object {
        const val LIMIT_RECENT_SEARCH = 10
    }

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val proseDbRef = db.getReference(EndPoints.PROSE)

    override suspend fun getAllProse(): List<ProseVo> = suspendCoroutine {coroutineScope ->
        val proseList: MutableList<ProseVo> = mutableListOf()
        proseDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val prose = dataSnapshot.getValue(ProseVo::class.java)
                        prose?.let {
                            proseList.add(it)
                        }
                    }
                    coroutineScope.resume(proseList)
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.resume(proseList)
                }
            })
    }

    override suspend fun getProse(request: Int): ProseVo = suspendCoroutine { coroutineScope ->
        val proseId = request.toString()
        proseDbRef.child(proseId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val prose = snapshot.getValue(ProseVo::class.java)
                    prose?.let { coroutineScope.resume(it) }
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.resume(ProseVo())
                }
            })

    }

    override suspend fun likeCancel(request: LikeVo): Boolean = coroutineScope {
        val success = async {
            removeLikedUser(request.pageId)
        }.await()

        if (success) updateLikeCount(request.pageId) else false
    }

    override suspend fun likeAdd(request: LikeVo): Boolean = coroutineScope {
        val success = async {
            addLikedUser(request)
        }.await()

        if (success) updateLikeCount(request.pageId) else false
    }

    private suspend fun removeLikedUser(proseId: Int): Boolean = suspendCoroutine {
        val proseRef = proseDbRef.child(proseId.toString())
        val likedUsersRef = proseRef.child(EndPoints.LIKED_MEMBER).child(auth.uid.toString())

        likedUsersRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                it.resume(true)
            } else {
                it.resume(false)
            }
        }
    }

    private suspend fun updateLikeCount(proseId: Int): Boolean = suspendCoroutine { continuation ->
        val proseRef = proseDbRef.child(proseId.toString())
        val likedUsersRef = proseRef.child(EndPoints.LIKED_MEMBER)

        likedUsersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val likedUserCount = snapshot.childrenCount.toInt()
                proseRef.child(EndPoints.LIKE_COUNT).setValue(likedUserCount)
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener { e ->
                        continuation.resume(false)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resume(false)
            }
        })
    }


    private suspend fun addLikedUser(request: LikeVo): Boolean = suspendCoroutine { continuation ->
        proseDbRef.child(request.pageId.toString()).child(EndPoints.LIKED_MEMBER).child(auth.uid.toString())
            .setValue(request.nickName)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }

    }

    override suspend fun addComment(request: UpdateCommentVo): Boolean = coroutineScope {
        val success = async {
            addProseComment(request)
        }.await()

        if (success) updateCommentCount(request.id) else false
    }

    override suspend fun deleteComment(request: UpdateCommentVo): Boolean = coroutineScope {
        val success = async {
            deleteProseComment(request)
        }.await()

        if (success) updateCommentCount(request.id) else false
    }

    private suspend fun deleteProseComment(request: UpdateCommentVo): Boolean = suspendCoroutine {
        proseDbRef.child(request.id.toString() + "번").child(EndPoints.COMMENT)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (commentSnapshot in dataSnapshot.children) {
                        val commentId =
                            commentSnapshot.child(EndPoints.COMMENT_ID).getValue(Int::class.java)
                        if (commentId == request.comment.commentId) {
                            commentSnapshot.ref.removeValue().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    it.resume(true)
                                } else {
                                    it.resume(false)
                                }
                            }
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    it.resume(false)
                }
            })
    }

    override suspend fun upload(request: ProseVo): Boolean = suspendCoroutine { continuation ->
        var lastProseId = 0
        proseDbRef.orderByChild(EndPoints.PROSE_ID).limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val prose = snapshot.getValue(ProseVo::class.java)
                        prose?.let {
                            lastProseId = it.proseId + 1
                        }
                    }
                    val newRequest = request.copy(proseId = lastProseId)

                    db.getReference(EndPoints.PROSE).child(lastProseId.toString())
                        .setValue(newRequest)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                addUserProse(newRequest) { isSuccess ->
                                    continuation.resume(isSuccess)
                                }
                            } else {
                                continuation.resume(false)
                            }
                        }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resume(false)
                }
            })
    }

    private fun addUserProse(prose: ProseVo, callback: (Boolean) -> Unit) {
        val userProseRef = db.getReference(EndPoints.AUTH).child(DataUserInfo.info.nickName).child(EndPoints.MY_PROSE)
        val proseId = prose.proseId.toString()

        userProseRef.child(proseId + "번").setValue(prose)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }


    override suspend fun update(request: ProseVo): Boolean = suspendCoroutine {
        proseDbRef.child(request.proseId.toString())
            .setValue(request)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.resume(true)
                } else {
                    it.resume(false)
                }
            }

    }

    override suspend fun deleteProse(request: Int): Boolean = suspendCoroutine {
        proseDbRef.child(request.toString()).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    deleteMyProse(request) { isSuccess ->
                        it.resume(isSuccess)
                    }
                } else {
                    it.resume(false)
                }
            }
    }

    private fun deleteMyProse(proseId: Int, callback: (Boolean) -> Unit){
        db.getReference(EndPoints.AUTH).child(DataUserInfo.info.nickName).child(EndPoints.MY_PROSE).child(proseId.toString()+ "번")
            .removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    override suspend fun getRecentSearch(): List<String> {
        val list = mutableListOf<String>()
        recentSearchDao.getRecentSearches(LIMIT_RECENT_SEARCH).forEach {
            list.add(it.search)
        }
        return list
    }

    override suspend fun insertRecentSearch(text: String): Boolean {
        recentSearchDao.insertProse(
            RecentSearchProseEntity(
                search = text,
                saveTime = System.currentTimeMillis(),
            )
        )
        return true
    }

    override suspend fun getSearchedResult(request: SearchVo): List<ProseVo> = suspendCoroutine {
        val searchedProseList = mutableListOf<ProseVo>()
        proseDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (proseSnapshot in snapshot.children) {
                        val prose = proseSnapshot.getValue(ProseVo::class.java)
                        when (request.type) {
                            SearchType.TITLE -> {
                                if (prose != null && prose.title.contains(request.text)) {
                                    searchedProseList.add(prose)
                                }
                            }

                            SearchType.CONTENT -> {
                                if (prose != null && prose.content.contains(request.text)) {
                                    searchedProseList.add(prose)
                                }
                            }

                            SearchType.TITLE_CONTENT -> {
                                if (prose != null && (prose.title.contains(request.text) || prose.content.contains(request.text))
                                ) {
                                    searchedProseList.add(prose)
                                }
                            }
                        }
                    }

                    it.resume(searchedProseList)
                }

                override fun onCancelled(error: DatabaseError) {
                    it.resume(emptyList())
                }

            })
    }

    private suspend fun addProseComment(request: UpdateCommentVo): Boolean = suspendCoroutine {
        var newRequest : CommentVo
        var lastId = 0
        val dbRef = proseDbRef.child(request.id.toString()).child(EndPoints.COMMENT)
        dbRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val comment = snapshot.getValue(CommentVo::class.java)
                    if (comment != null) {
                        lastId = comment.commentId + 1
                    }
                }
                newRequest = request.comment.copy(commentId = lastId)
                dbRef.child(newRequest.commentId.toString() + "번").setValue(newRequest)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            it.resume(true)
                        } else {
                            it.resume(false)
                        }
                    }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
                return it.resume(false)
            }
        })

    }

    private suspend fun updateCommentCount(id: Int): Boolean = suspendCoroutine { continuation ->
        val proseRef = proseDbRef.child(id.toString())
        val commentRef = proseRef.child(EndPoints.COMMENT)
        commentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var commentCount = 0
                snapshot.children.forEach { child ->
                    if (child.value != null) {
                        commentCount++
                    }
                }

                proseRef.child(EndPoints.COMMENT_COUNT).setValue(commentCount)
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener { e ->
                        continuation.resume(false)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resume(false)
            }
        })
    }
}