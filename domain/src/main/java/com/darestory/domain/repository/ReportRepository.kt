package com.darestory.domain.repository

import com.darestory.domain.model.vo.ReportVo

interface ReportRepository {
    suspend fun report(request : ReportVo) : Boolean
}