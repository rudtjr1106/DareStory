package com.example.data.repository

import android.util.Log
import com.example.data.DataUserInfo
import com.example.data.EndPoints
import com.example.domain.model.vo.LoginVo
import com.example.domain.model.vo.UserVo
import com.example.domain.repository.SignRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SignRepositoryImpl @Inject constructor() : SignRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val authDbRef = db.getReference(EndPoints.AUTH)
    private lateinit var fcmToken : String

    override suspend fun signUp(requestEmail: String, requestPw: String): Boolean = suspendCoroutine{
        auth.createUserWithEmailAndPassword(requestEmail, requestPw).addOnCompleteListener {task ->
            if(task.isSuccessful){
                setFcmToken { isSuccess ->
                    it.resume(true)
                }
            }
            else{
                it.resume(false)
            }
        }
    }

    override suspend fun login(request: LoginVo): Boolean {
        return try {
            auth.signInWithEmailAndPassword(request.email, request.password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun logout(): Boolean {
        return try {
            auth.signOut()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun unRegister(): Boolean = suspendCoroutine {
        auth.currentUser?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                deleteUser { isSuccess ->
                    it.resume(isSuccess)
                }
            } else {
                it.resume(false)
            }
        }
    }

    private fun deleteUser(callback: (Boolean) -> Unit) {
        authDbRef.child(DataUserInfo.info.nickName).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true)
            } else {
                callback(false)
            }
        }
    }


    override suspend fun getAllNickName(): List<String> {
        return suspendCoroutine { continuation ->
            val nickNameList: MutableList<String> = mutableListOf()
            authDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
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

    override suspend fun addMyInfo(request: UserVo): Boolean = suspendCoroutine {
        val userVo = auth.uid?.let { request.copy(userUid = it, token = fcmToken) }
        val updatedData = mapOf(request.nickName to userVo)
        authDbRef.updateChildren(updatedData).addOnCompleteListener { task ->
            if(task.isSuccessful){
                it.resume(true)
            }
            else{
                it.resume(false)
            }
        }
    }

    private fun setFcmToken(callback: (Boolean) -> Unit){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fcmToken = task.result
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    override suspend fun getAllEmail(): List<String> {
        return suspendCoroutine { continuation ->
            val emailList: MutableList<String> = mutableListOf()
            authDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
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
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            false
        }

    }
    override suspend fun checkAutoLogin(): UserVo = suspendCoroutine {
        var userInfo = UserVo()
        authDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.getValue(UserVo::class.java)
                    if(user?.userUid == auth.uid){
                        userInfo = user!!
                    }
                }
                it.resume(userInfo)
            }

            override fun onCancelled(error: DatabaseError) {
                it.resume(userInfo)
            }
        })
    }
}