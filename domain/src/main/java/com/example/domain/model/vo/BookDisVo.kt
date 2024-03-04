package com.example.domain.model.vo

data class BookDisVo(
    val author : String = "",
    val bookDisId : Int = -1,
    val bookImage : String = "",
    val bookName : String = "",
    val commentCount : Int = 0,
    val content : String = "",
    val createdAt : String = "",
    val likeCount : Int = 0,
    val title : String = ""
)
