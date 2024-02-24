package com.example.domain.repository

import com.example.domain.model.LoginVo
import com.example.domain.model.SignUpVo

interface SignRepository {
    suspend fun signUp(request : SignUpVo) : Boolean
    suspend fun login(request: LoginVo) : Boolean

    suspend fun getAllNickName() : List<String>

    suspend fun addMyInfo(request: SignUpVo) : Boolean
    suspend fun getAllEmail() : List<String>
}