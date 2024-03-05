package com.example.domain.repository

interface BookRepository {
    suspend fun searchBook(request : String)
}