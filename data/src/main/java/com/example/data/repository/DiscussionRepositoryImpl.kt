package com.example.data.repository

import android.util.Log
import com.example.data.EndPoints
import com.example.data.dao.RecentSearchDiscussionDao
import com.example.data.entitiy.RecentSearchDiscussionEntity
import com.example.domain.model.enums.SearchType
import com.example.domain.model.vo.CommentRequestVo
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.DisCommentVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.SearchVo
import com.example.domain.model.vo.UpdateCommentVo
import com.example.domain.model.vo.UpdateReplyCommentVo
import com.example.domain.repository.DiscussionRepository
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

class DiscussionRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDiscussionDao,
) : DiscussionRepository {

    companion object {
        const val LIMIT_RECENT_SEARCH = 10
    }


    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val discussionDbRef = db.getReference(EndPoints.DISCUSSION)
    override suspend fun getAllDiscussion() : List<DiscussionVo> = suspendCoroutine {coroutineScope ->
        val discussionList: MutableList<DiscussionVo> = mutableListOf()
        discussionDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val discussion = dataSnapshot.getValue(DiscussionVo::class.java)
                    discussion?.let {
                        discussionList.add(it)
                    }
                }
                coroutineScope.resume(discussionList)
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(discussionList)
            }
        })
    }

    override suspend fun getDiscussion(request: Int): DiscussionVo = suspendCoroutine { coroutineScope ->
        discussionDbRef.child(request.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val discussion = snapshot.getValue(DiscussionVo::class.java)
                discussion?.let { coroutineScope.resume(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(DiscussionVo())
            }
        })

    }

    override suspend fun upload(request: DiscussionVo): Boolean = suspendCoroutine {
        var lastDiscussionId = 0
        discussionDbRef.orderByChild(EndPoints.DISCUSSION_ID).limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val discussion = snapshot.getValue(DiscussionVo::class.java)
                        discussion?.let {
                            lastDiscussionId = it.discussionId + 1
                        }
                    }
                    val newRequest = request.copy(discussionId = lastDiscussionId)

                    discussionDbRef.child(lastDiscussionId.toString())
                        .setValue(newRequest).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                it.resume(true)
                            } else {
                                it.resume(false)
                            }
                        }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    it.resume(false)
                }
            })

    }

    override suspend fun update(request: DiscussionVo): Boolean = suspendCoroutine {
        discussionDbRef.child(request.discussionId.toString())
            .setValue(request)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.resume(true)
                } else {
                    it.resume(false)
                }
            }
    }

    override suspend fun addComment(request: UpdateCommentVo): Boolean = coroutineScope {
        val success = async {
            addDiscussionComment(request)
        }.await()

        if (success) updateCommentCount(request.id) else false
    }

    override suspend fun addReplyComment(request: UpdateReplyCommentVo): Boolean = suspendCoroutine {
        var newRequest : CommentVo
        var lastId = 0
        val dbRef = discussionDbRef.child(request.id.toString()).child(EndPoints.COMMENT)
            .child(request.commentId.toString())
            .child(EndPoints.REPLY_COMMENT)
        dbRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val comment = snapshot.getValue(CommentVo::class.java)
                    if (comment != null) {
                        lastId = comment.commentId + 1
                    }
                }
                newRequest = request.comment.copy(commentId = lastId)
                dbRef.child(newRequest.commentId.toString()).setValue(newRequest)
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

    override suspend fun deleteComment(request: UpdateCommentVo): Boolean = coroutineScope {
        val success = async {
            deleteDiscussionComment(request)
        }.await()

        if (success) updateCommentCount(request.id) else false
    }

    override suspend fun deleteReplyComment(request: UpdateReplyCommentVo): Boolean = suspendCoroutine {
        discussionDbRef.child(request.id.toString()).child(EndPoints.COMMENT).child(request.commentId.toString()).child(EndPoints.REPLY_COMMENT)
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
    private suspend fun deleteDiscussionComment(request: UpdateCommentVo): Boolean = suspendCoroutine {
        discussionDbRef.child(request.id.toString()).child(EndPoints.COMMENT)
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

    private suspend fun addDiscussionComment(request: UpdateCommentVo): Boolean = suspendCoroutine {
        var newRequest : CommentVo
        var lastId = 0
        val dbRef = discussionDbRef.child(request.id.toString()).child(EndPoints.COMMENT)
        dbRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val comment = snapshot.getValue(CommentVo::class.java)
                    if (comment != null) {
                        lastId = comment.commentId + 1
                    }
                }
                newRequest = request.comment.copy(commentId = lastId)
                dbRef.child(newRequest.commentId.toString()).setValue(newRequest)
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
        val discussionRef = discussionDbRef.child(id.toString())
        val commentRef = discussionRef.child(EndPoints.COMMENT)
        commentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var commentCount = 0
                snapshot.children.forEach { child ->
                    if (child.value != null) {
                        commentCount++
                    }
                }

                discussionRef.child(EndPoints.COMMENT_COUNT).setValue(commentCount)
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

    private suspend fun removeLikedUser(discussionId: Int): Boolean = suspendCoroutine {
        val discussionRef = discussionDbRef.child(discussionId.toString())
        val likedUsersRef = discussionRef.child(EndPoints.LIKED_MEMBER).child(auth.uid.toString())

        likedUsersRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                it.resume(true)
            } else {
                it.resume(false)
            }
        }
    }

    private suspend fun addLikedUser(request: LikeVo): Boolean = suspendCoroutine { continuation ->
        discussionDbRef.child(request.pageId.toString()).child(EndPoints.LIKED_MEMBER).child(auth.uid.toString())
            .setValue(request.nickName)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }

    }

    private suspend fun updateLikeCount(discussionId: Int): Boolean = suspendCoroutine { continuation ->
        val discussionRef = discussionDbRef.child(discussionId.toString())
        val likedUsersRef = discussionRef.child(EndPoints.LIKED_MEMBER)

        likedUsersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val likedUserCount = snapshot.childrenCount.toInt()
                discussionRef.child(EndPoints.LIKE_COUNT).setValue(likedUserCount)
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

    override suspend fun deleteDiscussion(request: Int): Boolean = suspendCoroutine {
        discussionDbRef.child(request.toString()).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.resume(true)
                } else {
                    it.resume(false)
                }
            }
    }

    override suspend fun getDiscussionReplyComment(request: CommentRequestVo): DisCommentVo = suspendCoroutine{ coroutineScope ->
        discussionDbRef.child(request.discussionId.toString()).child(EndPoints.COMMENT).child(request.commentId.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val replyComment = snapshot.getValue(DisCommentVo::class.java)
                replyComment?.let { coroutineScope.resume(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(DisCommentVo())
            }
        })
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
            RecentSearchDiscussionEntity(
                search = text,
                saveTime = System.currentTimeMillis(),
            )
        )
        return true
    }

    override suspend fun getSearchedResult(request: SearchVo): List<DiscussionVo> = suspendCoroutine {
        val searchedDiscussionList = mutableListOf<DiscussionVo>()
        discussionDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (discussionSnapshot in snapshot.children) {
                    val discussion = discussionSnapshot.getValue(DiscussionVo::class.java)
                    Log.d("여기 데이터", discussion.toString())
                    when (request.type) {
                        SearchType.TITLE -> {
                            if (discussion != null && discussion.title.contains(request.text)) {
                                searchedDiscussionList.add(discussion)
                            }
                        }

                        SearchType.CONTENT -> {
                            if (discussion != null && discussion.content.contains(request.text)) {
                                searchedDiscussionList.add(discussion)
                            }
                        }

                        SearchType.TITLE_CONTENT -> {
                            if (discussion != null && (discussion.title.contains(request.text) || discussion.content.contains(request.text))
                            ) {
                                searchedDiscussionList.add(discussion)
                            }
                        }
                    }
                }

                it.resume(searchedDiscussionList)
            }

            override fun onCancelled(error: DatabaseError) {
                it.resume(emptyList())
            }

        })
    }
}