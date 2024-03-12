package com.darestory.domain.model.vo

data class CommentVo(
    val commentId : Int = -1,
    val content : String = "",
    val date : String = "",
    val token : String = "",
    val writer : String = ""
)