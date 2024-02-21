package com.example.data.repository

import android.util.Log
import com.example.domain.model.SignUpVo
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor() : SignRepository {
    override suspend fun signUp(request: SignUpVo): Boolean {
        Log.d("여기 데이터", "실행 잘됨 email : ${request.email}\n 비번 : ${request.password}")
        return true
    }
}