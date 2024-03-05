package com.example.data.repository

import android.util.Log
import com.example.domain.model.vo.BookResponseVo
import com.example.domain.model.vo.BookVo
import com.example.domain.repository.BookRepository
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    val clientId = "nrH71bqWh3JJHWLjL8VM"
    val clientSecret = "CDFjlzTNq2"

    override suspend fun searchBook(request: String) {
        val text = URLEncoder.encode("$request", "UTF-8")
        println(text)
        val url = URL("https://openapi.naver.com/v1/search/book.json?query=${text}&display=10&start=1")
        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", clientId)
            .addHeader("X-Naver-Client-Secret", clientSecret)
            .method("GET", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val bookVo = gson.fromJson(body, BookResponseVo::class.java)
                Log.d("여기 데이터", bookVo.toString())

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }
}