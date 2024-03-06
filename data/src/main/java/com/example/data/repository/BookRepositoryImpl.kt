package com.example.data.repository

import com.example.data.NaverClient
import com.example.domain.model.vo.BookResponseVo
import com.example.domain.repository.BookRepository
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BookRepositoryImpl @Inject constructor() : BookRepository {

    override suspend fun searchBook(request: String) : BookResponseVo = suspendCoroutine{
        val text = URLEncoder.encode("$request", "UTF-8")
        println(text)
        val url = URL("https://openapi.naver.com/v1/search/book.json?query=${text}&display=100&start=1")
        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", NaverClient.getClientId())
            .addHeader("X-Naver-Client-Secret", NaverClient.getClientSecret())
            .method("GET", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val bookVo = gson.fromJson(body, BookResponseVo::class.java)
                it.resume(bookVo)
            }

            override fun onFailure(call: Call?, e: IOException?) {
                it.resume(BookResponseVo())
            }
        })
    }
}