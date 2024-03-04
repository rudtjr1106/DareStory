package com.example.data.repository

import com.example.data.EndPoints
import com.example.domain.model.vo.DiscussionVo
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
}