package com.example.domain.repository

import com.example.domain.model.SignUpVo

interface SignRepository {
    suspend fun signUp(request : SignUpVo) : Boolean
}