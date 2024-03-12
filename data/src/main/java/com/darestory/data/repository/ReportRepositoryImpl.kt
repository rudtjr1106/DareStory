package com.darestory.data.repository

import com.darestory.data.EndPoints
import com.darestory.domain.model.vo.ReportVo
import com.darestory.domain.repository.ReportRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ReportRepositoryImpl @Inject constructor() : ReportRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    override suspend fun report(request: ReportVo): Boolean = suspendCoroutine {
        db.getReference(EndPoints.REPORT).child(request.userName + auth.uid).setValue(request)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    it.resume(true)
                }
                else{
                    it.resume(false)
                }
            }
    }

}