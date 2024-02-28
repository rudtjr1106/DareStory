package com.example.data.repository

import com.example.data.EndPoints
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.UserVo
import com.example.domain.repository.ProseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProseRepositoryImpl @Inject constructor() : ProseRepository {

    private val db = FirebaseDatabase.getInstance()
    override suspend fun getAllProse(): List<ProseVo> {
        return suspendCoroutine { continuation ->
            val proseList: MutableList<ProseVo> = mutableListOf()
            db.getReference(EndPoints.PROSE).addListenerForSingleValueEvent(object : ValueEventListener {
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

}