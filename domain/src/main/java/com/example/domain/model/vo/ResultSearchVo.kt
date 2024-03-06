package com.example.domain.model.vo

import com.example.domain.model.enums.DetailType

data class ResultSearchVo(
    val proseVo: ProseVo = ProseVo(),
    val discussionVo: DiscussionVo = DiscussionVo(),
    val bookVo: BookVo = BookVo(),
    val type : DetailType = DetailType.PROSE
)