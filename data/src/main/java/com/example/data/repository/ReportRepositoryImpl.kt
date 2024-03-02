package com.example.data.repository

import com.example.data.EndPoints
import com.example.domain.model.vo.ReportVo
import com.example.domain.repository.ReportRepository
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ReportRepositoryImpl @Inject constructor() : ReportRepository {

    private val db = FirebaseDatabase.getInstance()
    override suspend fun report(request: ReportVo): Boolean = suspendCoroutine {
        db.getReference(EndPoints.REPORT).child(request.userName).setValue(request)
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