package com.example.domain.model.vo

data class DisCommentVo(
    val commentId : Int = -1,
    val content : String = "",
    val date : String = "",
    val replyComment : CommentVo = CommentVo(),
    val writer : String = ""
)