package com.example.domain.repository

import com.example.domain.model.vo.UserVo

interface MyRepository {
    suspend fun getMyInfo(request : String) : UserVo
}