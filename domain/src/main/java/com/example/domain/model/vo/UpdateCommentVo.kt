package com.example.domain.model.vo

data class UpdateCommentVo(
    val id : Int = -1,
    val comment : CommentVo = CommentVo()
)