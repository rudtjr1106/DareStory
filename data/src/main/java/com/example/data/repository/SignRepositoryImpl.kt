package com.example.data.repository

import android.util.Log
import com.example.data.EndPoints
import com.example.domain.model.LoginVo
import com.example.domain.model.SignUpVo
import com.example.domain.repository.SignRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor() : SignRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    override suspend fun signUp(request: SignUpVo): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(request.email, request.password).await()
            true
        }
        catch (e: FirebaseAuthException){
            false
        }
    }

    override suspend fun login(request: LoginVo): Boolean {
        Log.d("여기 데이터", "실행 잘됨 email : ${request.email}\n 비번 : ${request.password}")
        return true
    }

    override suspend fun getAllNickName(): List<String> {
        val snapshot = db.collection(EndPoints.AUTH).get().await()
        val list = mutableListOf<String>()
        snapshot.documents.forEach {
            list.add(it.id)
            Log.d("여기 데이터", "닉넴 가져오기 성공 ${it.id.toString()}")
        }
        return list
    }

    override suspend fun addMyInfo(request: SignUpVo) : Boolean{
        return try {
            db.collection(EndPoints.AUTH).document(request.nickname).set(request).await()
            true
        }
        catch (e : FirebaseFirestoreException){
            false
        }
    }

    override suspend fun getAllEmail(): List<String> {
        val snapshot = db.collection(EndPoints.AUTH).get().await()
        val list = mutableListOf<String>()
        snapshot.documents.forEach {
            list.add(it.getString(EndPoints.AUTH_EMAIL).toString())
        }
        return list
    }
}