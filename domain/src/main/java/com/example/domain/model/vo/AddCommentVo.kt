package com.example.domain.model.vo

data class AddCommentVo(
    val id : Int = -1,
    val comment : CommentVo = CommentVo()
)