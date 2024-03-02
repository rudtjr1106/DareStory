package com.example.domain.repository

import com.example.domain.model.vo.ReportVo

interface ReportRepository {
    suspend fun report(request : ReportVo) : Boolean
}