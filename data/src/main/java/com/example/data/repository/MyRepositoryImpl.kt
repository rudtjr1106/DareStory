package com.example.data.repository

import android.util.Log
import com.example.data.EndPoints
import com.example.domain.model.vo.DiscussionVo
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
                Log.d("여기 데이터", userVo.toString())
                userVo?.let { coroutineScope.resume(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(UserVo())
            }
        })
    }
}