package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.CommentType

data class DiscussionCommentPageVo(
    val commentVo: DisCommentVo = DisCommentVo(),
    val type : CommentType = CommentType.NORMAL
)