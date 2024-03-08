package com.example.data.repository

import android.util.Log
import com.example.data.DataUserInfo
import com.example.data.EndPoints
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.UserVo
import com.example.domain.repository.MyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyRepositoryImpl @Inject constructor() : MyRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val myDbRef = db.getReference(EndPoints.AUTH)
    override suspend fun getMyInfo(request: String): UserVo = suspendCoroutine { coroutineScope ->
        myDbRef.child(request).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userVo = snapshot.getValue(UserVo::class.java)
                userVo?.let {
                    DataUserInfo.updateInfo(it)
                    coroutineScope.resume(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(UserVo())
            }
        })
    }

    override suspend fun getMyDiscussion(): List<DiscussionVo> = suspendCoroutine { coroutineScope ->
        val discussionList: MutableList<DiscussionVo> = mutableListOf()
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_DISCUSSION)
            .addListenerForSingleValueEvent(object : ValueEventListener {
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
                coroutineScope.resume(emptyList())
            }
        })
    }

    override suspend fun getMyProse(): List<ProseVo> = suspendCoroutine { coroutineScope ->
        val proseList: MutableList<ProseVo> = mutableListOf()
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_PROSE)
            .addListenerForSingleValueEvent(object : ValueEventListener {
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
                    coroutineScope.resume(emptyList())
                }
            })
    }
}