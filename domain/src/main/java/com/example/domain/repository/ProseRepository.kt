package com.example.domain.repository

import com.example.domain.model.vo.ProseVo

interface ProseRepository {
    suspend fun getAllProse() : List<ProseVo>
    suspend fun getProse(request : Int) : ProseVo
}