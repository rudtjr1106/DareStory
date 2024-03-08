package com.example.domain.model.vo

data class ProseVo(
    val age : String = "",
    val author : String = "",
    val authorSay : String = "",
    val comment : HashMap<String, CommentVo> = hashMapOf(),
    val commentCount : Int = 0,
    val content : String = "",
    val createdAt : String = "",
    val likeCount : Int = 0,
    val proseId : Int = -1,
    val title : String = "",
    val whoLiked : HashMap<String, String> = hashMapOf()
)
