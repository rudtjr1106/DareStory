package com.example.data.repository

import android.util.Log
import com.example.domain.model.LoginVo
import com.example.domain.model.SignUpVo
import com.example.domain.repository.SignRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor() : SignRepository {

    private lateinit var auth: FirebaseAuth
    override suspend fun signUp(request: SignUpVo): Boolean {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(request.email, request.password).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("여기 데이터", "실행 성공")
            }
            else{
                Log.d("여기 데이터", "실행 실패")
            }
        }
        return true
    }

    override suspend fun login(request: LoginVo): Boolean {
        Log.d("여기 데이터", "실행 잘됨 email : ${request.email}\n 비번 : ${request.password}")
        return true
    }
}