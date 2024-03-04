package com.example.data.repository

import com.example.data.EndPoints
import com.example.domain.model.vo.LoginVo
import com.example.domain.model.vo.UserVo
import com.example.domain.repository.SignRepository
import com.google.firebase.auth.FirebaseAuth
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
    private val authDbRef = db.getReference(EndPoints.AUTH)

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
            auth.signInWithEmailAndPassword(request.email, request.password).await()
            true
        }
        catch (e: Exception) {
            false
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

    override suspend fun addMyInfo(request: UserVo) : Boolean{
        val userVo = auth.uid?.let { request.copy(userUid = it) }
        val updatedData = mapOf(request.nickName to userVo)
        return try {
            authDbRef.updateChildren(updatedData).await()
            true
        }
        catch (e : Exception){
            false
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

    override suspend fun checkAutoLogin(): UserVo {
        val currentUser = auth.currentUser
        return suspendCoroutine { continuation ->
            authDbRef.orderByChild(EndPoints.AUTH_UID).equalTo(currentUser?.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (childSnapshot in snapshot.children) {
                            val userInfoMap = childSnapshot.value as? Map<String, Any>
                            userInfoMap?.let {
                                val age = it["age"] as? String ?: ""
                                val email = it["email"] as? String ?: ""
                                val gender = it["gender"] as? String ?: ""
                                val nickName = it["nickName"] as? String ?: ""
                                val userUid = it["userUid"] as? String ?: ""
                                val userVo = UserVo(age, email, gender, nickName, userUid)
                                continuation.resume(userVo)
                                return
                            }
                        }
                    }
                    continuation.resume(UserVo())
                }
                override fun onCancelled(error: DatabaseError) {
                    continuation.resume(UserVo())
                }
            })
        }
    }
}