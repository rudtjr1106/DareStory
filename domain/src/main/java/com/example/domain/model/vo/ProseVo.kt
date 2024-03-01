package com.example.domain.model.vo

data class ProseVo(
    val author : String = "",
    val comment : HashMap<String,CommentVo> = hashMapOf(),
    val commentCount : Int = 0,
    val content : String = "",
    val createdAt : String = "",
    val likeCount : Int = 0,
    val proseId : Int = 0,
    val title : String = "",
    val whoLiked : HashMap<String, String> = hashMapOf()
)
