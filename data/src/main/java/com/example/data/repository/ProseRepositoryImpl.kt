package com.example.data.repository

import com.example.data.EndPoints
import com.example.domain.model.vo.UpdateCommentVo
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.ProseVo
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

class ProseRepositoryImpl @Inject constructor() : ProseRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    override suspend fun getAllProse(): List<ProseVo> {
        return suspendCoroutine { continuation ->
            val proseList: MutableList<ProseVo> = mutableListOf()
            db.getReference(EndPoints.PROSE)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (dataSnapshot in snapshot.children) {
                            val prose = dataSnapshot.getValue(ProseVo::class.java)
                            prose?.let {
                                proseList.add(it)
                            }
                        }
                        continuation.resume(proseList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(proseList)
                    }
                })
        }
    }

    override suspend fun getProse(request: Int): ProseVo {
        val proseId = request.toString()
        return suspendCoroutine { continuation ->
            db.getReference(EndPoints.PROSE).child(proseId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val prose = snapshot.getValue(ProseVo::class.java)
                        prose?.let { continuation.resume(it) }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(ProseVo())
                    }
                })
        }
    }

    override suspend fun likeCancel(request: LikeVo): Boolean = coroutineScope {
        val success = async {
            removeLikedUser(request.id)
        }.await()

        if(success) updateLikeCount(request.id) else false
    }

    override suspend fun likeAdd(request: LikeVo): Boolean = coroutineScope {
        val success = async {
            addLikedUser(request)
        }.await()

        if(success) updateLikeCount(request.id) else false
    }

    private suspend fun removeLikedUser(proseId: Int): Boolean = suspendCoroutine { continuation ->
        val proseRef = db.getReference(EndPoints.PROSE).child(proseId.toString())
        val likedUsersRef = proseRef.child(EndPoints.LIKED_MEMBER).child(auth.uid.toString())

        likedUsersRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(true)
            } else {
                continuation.resume(false)
            }
        }
    }

    private suspend fun updateLikeCount(proseId: Int): Boolean = suspendCoroutine { continuation ->
        val proseRef = db.getReference(EndPoints.PROSE).child(proseId.toString())
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


    private suspend fun addLikedUser(request: LikeVo): Boolean {
        return suspendCoroutine { continuation ->
            db.getReference(EndPoints.PROSE).child(request.id.toString()).child(EndPoints.LIKED_MEMBER)
                .child(auth.uid.toString()).setValue(request.nickName)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
        }
    }

    override suspend fun addComment(request: UpdateCommentVo): Boolean = coroutineScope {
        val success = async {
            addProseComment(request)
        }.await()

        if(success) updateCommentCount(request.id) else false
    }

    override suspend fun deleteComment(request: UpdateCommentVo): Boolean = coroutineScope {
        val success = async {
            deleteProseComment(request)
        }.await()

        if(success) updateCommentCount(request.id) else false
    }

    private suspend fun deleteProseComment(request: UpdateCommentVo) : Boolean = suspendCoroutine {
        db.getReference(EndPoints.PROSE).child(request.id.toString()).child(EndPoints.COMMENT)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (commentSnapshot in dataSnapshot.children) {
                    val commentId = commentSnapshot.child(EndPoints.COMMENT_ID).getValue(Int::class.java)
                    if (commentId == request.comment.commentId) {
                        commentSnapshot.ref.removeValue()
                            .addOnCompleteListener { task ->
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

    override suspend fun upload(request: ProseVo): Boolean {
        return suspendCoroutine {
            db.getReference(EndPoints.PROSE).orderByChild(EndPoints.PROSE_ID).limitToLast(1)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var lastProseId = 0

                        for (snapshot in dataSnapshot.children) {
                            val prose = snapshot.getValue(ProseVo::class.java)
                            prose?.let {
                                lastProseId = it.proseId + 1
                            }
                        }
                        val newRequest = request.copy(proseId = lastProseId)

                        db.getReference(EndPoints.PROSE).child(lastProseId.toString())
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
    }

    override suspend fun update(request: ProseVo): Boolean {
        return suspendCoroutine {
            db.getReference(EndPoints.PROSE).child(request.proseId.toString())
                .setValue(request).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        it.resume(true)
                    } else {
                        it.resume(false)
                    }
                }
        }
    }

    override suspend fun deleteProse(request: Int): Boolean {
        return suspendCoroutine {
            db.getReference(EndPoints.PROSE).child(request.toString()).removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        it.resume(true)
                    } else {
                        it.resume(false)
                    }
                }
        }
    }

    private suspend fun addProseComment(request: UpdateCommentVo) : Boolean{
        var newRequest = CommentVo()
        var lastId = 0
        val dbRef = db.getReference(EndPoints.PROSE).child(request.id.toString()).child(EndPoints.COMMENT)
        return suspendCoroutine {
            dbRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val comment = snapshot.getValue(CommentVo::class.java)
                        if (comment != null) {
                            lastId = comment.commentId + 1
                        }
                    }
                    newRequest = request.comment.copy(commentId = lastId)
                    dbRef.child(newRequest.commentId.toString()).setValue(newRequest).addOnCompleteListener { task ->
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
    }

    private suspend fun updateCommentCount(id : Int) : Boolean = suspendCoroutine { continuation ->
        val proseRef = db.getReference(EndPoints.PROSE).child(id.toString())
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