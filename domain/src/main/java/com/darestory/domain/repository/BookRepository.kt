package com.darestory.domain.repository

import com.darestory.domain.model.vo.BookResponseVo

interface BookRepository {
    suspend fun searchBook(request : String) : BookResponseVo
}