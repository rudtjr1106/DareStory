package com.example.domain.model.vo

data class UpdateReplyCommentVo(
    val id : Int = -1,
    val commentId : Int = -1,
    val comment : CommentVo = CommentVo()
)