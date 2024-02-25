package com.example.domain.model.sign

data class BookDisVo(
    val author : String? = "",
    val bookDisId : Int? = 0,
    val bookImage : String? = "",
    val bookName : String? = "",
    val commentCount : Int? = 0,
    val content : String? = "",
    val createdAt : String? = "",
    val likeCount : Int? = 0,
    val title : String? = ""
)
