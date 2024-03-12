package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.DiscussionViewType
data class MainDiscussionVo(
    val discussionVo : DiscussionVo = DiscussionVo(),
    val viewType: DiscussionViewType = DiscussionViewType.TOP
)
