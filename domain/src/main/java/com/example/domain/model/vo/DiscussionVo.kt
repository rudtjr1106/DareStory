package com.example.domain.model.vo

data class DiscussionVo(
    val age : String = "",
    val author : String = "",
    val bookAuthor : String = "",
    val bookImage : String = "",
    val bookTitle : String = "",
    val comment : List<DisCommentVo> = emptyList(),
    val commentCount : Int = 0,
    val content : String = "",
    val createdAt : String = "",
    val discussionId : Int = -1,
    val likeCount : Int = 0,
    val title : String = "",
    val whoLiked : HashMap<String, String> = hashMapOf()
)
