package com.example.domain.model.vo

import com.example.domain.model.enums.CommentType

data class DiscussionCommentPageVo(
    val commentVo: DisCommentVo = DisCommentVo(),
    val type : CommentType = CommentType.NORMAL
)