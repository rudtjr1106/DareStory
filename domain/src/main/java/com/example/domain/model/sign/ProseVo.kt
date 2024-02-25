package com.example.domain.model.sign

data class ProseVo(
    val author : String? = "",
    val commentCount : Int = 0,
    val content : String? = "",
    val createdAt : String? = "",
    val likeCount : Int? = 0,
    val proseId : Int? = 0,
    val title : String? = "",
)
