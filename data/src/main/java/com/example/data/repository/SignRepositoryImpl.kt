package com.example.data.repository

import android.util.Log
import com.example.data.EndPoints
import com.example.domain.model.sign.LoginVo
import com.example.domain.model.sign.UserVo
import com.example.domain.repository.SignRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SignRepositoryImpl @Inject constructor() : SignRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()

    override suspend fun signUp(requestEmail: String, requestPw: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(requestEmail, requestPw).await()
            true
        }
        catch (e: Exception){
            false
        }
    }

    override suspend fun login(request: LoginVo): Boolean {
        return try {
            auth?.signInWithEmailAndPassword(request.email, request.password)?.await()
            true
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun getAllNickName(): List<String> {
        return suspendCoroutine { continuation ->
            val nickNameList: MutableList<String> = mutableListOf()
            db.getReference(EndPoints.AUTH).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        nickNameList.add(dataSnapshot.key.toString())
                    }
                    continuation.resume(nickNameList)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override suspend fun addMyInfo(request: UserVo) : Boolean{
        val userVo = auth.uid?.let { request.copy(userUid = it) }
        val updatedData = mapOf(request.nickName to userVo)
        return try {
            db.getReference(EndPoints.AUTH).updateChildren(updatedData).await()
            true
        }
        catch (e : Exception){
            false
        }
    }

    override suspend fun getAllEmail(): List<String> {
        return suspendCoroutine { continuation ->
            val emailList: MutableList<String> = mutableListOf()
            db.getReference(EndPoints.AUTH).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val user = dataSnapshot.getValue(UserVo::class.java)
                        user?.let {
                            emailList.add(it.email)
                        }
                    }
                    continuation.resume(emailList)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override suspend fun sendEmailVerification(): Boolean {
        val user = auth.currentUser
        return try {
            user?.sendEmailVerification()?.await()
            true
        }
        catch (e : Exception){
            false
        }
    }

    override suspend fun checkEmailVerified(): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.reload()
        var isEmailVerified = currentUser?.isEmailVerified
        return isEmailVerified == true
    }

    override suspend fun sendPasswordResetEmail(request: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(request).await()
            true
        }
        catch (e : Exception){
            false
        }

    }
}