package com.darestory.domain.repository

import com.darestory.domain.model.vo.AppInfoResponseVo
import com.darestory.domain.model.vo.LoginVo
import com.darestory.domain.model.vo.UserVo

interface SignRepository {
    suspend fun signUp(requestEmail : String, requestPw : String) : Boolean
    suspend fun logout() : Boolean
    suspend fun unRegister() : Boolean
    suspend fun login(request: LoginVo) : Boolean

    suspend fun getAllNickName() : List<String>

    suspend fun addMyInfo(request: UserVo) : Boolean
    suspend fun getAllEmail() : List<String>
    suspend fun sendEmailVerification() : Boolean
    suspend fun checkEmailVerified() : Boolean
    suspend fun sendPasswordResetEmail(request : String) : Boolean
    suspend fun checkAutoLogin() : UserVo
    suspend fun checkAppInfo() : AppInfoResponseVo
}