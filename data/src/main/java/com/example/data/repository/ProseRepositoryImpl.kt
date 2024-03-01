package com.example.data.repository

import android.util.Log
import com.example.data.EndPoints
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.ProseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
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

    override suspend fun likeCancel(request: LikeVo): Boolean {
        return suspendCoroutine { continuation ->
            db.getReference(EndPoints.PROSE).child(request.id.toString()).child(EndPoints.PROSE_LIKED)
                .child(auth.uid.toString())
                .removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
        }
    }

    override suspend fun likeAdd(request: LikeVo): Boolean {
        return suspendCoroutine { continuation ->
            db.getReference(EndPoints.PROSE).child(request.id.toString()).child(EndPoints.PROSE_LIKED)
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
}