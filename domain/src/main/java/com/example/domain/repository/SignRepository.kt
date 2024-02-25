package com.example.domain.repository

import com.example.domain.model.sign.LoginVo
import com.example.domain.model.sign.SignUpVo
import com.example.domain.model.sign.UserVo

interface SignRepository {
    suspend fun signUp(requestEmail : String, requestPw : String) : Boolean
    suspend fun login(request: LoginVo) : Boolean

    suspend fun getAllNickName() : List<String>

    suspend fun addMyInfo(request: UserVo) : Boolean
    suspend fun getAllEmail() : List<String>
}