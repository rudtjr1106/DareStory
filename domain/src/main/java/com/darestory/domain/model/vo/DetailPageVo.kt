package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.DetailPageViewType
data class DetailPageVo(
    val proseComment: CommentVo = CommentVo(),
    val discussionComment : DisCommentVo = DisCommentVo(),
    val detailContent : DetailContentVo = DetailContentVo(),
    val detailViewType: DetailPageViewType = DetailPageViewType.CONTENT
)
