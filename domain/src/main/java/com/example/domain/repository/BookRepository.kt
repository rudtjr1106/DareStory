package com.example.domain.repository

import com.example.domain.model.vo.BookResponseVo

interface BookRepository {
    suspend fun searchBook(request : String) : BookResponseVo
}