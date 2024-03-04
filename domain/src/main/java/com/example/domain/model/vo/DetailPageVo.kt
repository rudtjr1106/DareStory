package com.example.domain.model.vo

import com.example.domain.model.enums.DetailPageViewType
data class DetailPageVo(
    val proseComment: CommentVo = CommentVo(),
    val detailContent : DetailContentVo = DetailContentVo(),
    val detailViewType: DetailPageViewType = DetailPageViewType.CONTENT
)
