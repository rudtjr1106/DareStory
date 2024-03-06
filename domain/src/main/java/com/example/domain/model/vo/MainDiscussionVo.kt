package com.example.domain.model.vo

import com.example.domain.model.enums.DiscussionViewType
data class MainDiscussionVo(
    val discussionVo : DiscussionVo = DiscussionVo(),
    val viewType: DiscussionViewType = DiscussionViewType.TOP
)
