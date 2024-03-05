package com.example.domain.model.vo

data class DisCommentVo(
    val commentId : Int = -1,
    val content : String = "",
    val date : String = "",
    val replyComment : List<CommentVo> = emptyList(),
    val writer : String = ""
)