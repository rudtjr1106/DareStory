package com.example.data.repository

import com.example.data.EndPoints
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.DiscussionRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DiscussionRepositoryImpl @Inject constructor() : DiscussionRepository {

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
}