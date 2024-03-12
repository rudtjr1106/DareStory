package com.darestory.domain.model.vo

data class DisCommentVo(
    val commentId : Int = -1,
    val content : String = "",
    val date : String = "",
    val replyComment : HashMap<String, CommentVo> = hashMapOf(),
    val token : String = "",
    val writer : String = ""
)