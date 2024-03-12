package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.HomeViewType

data class HomeProseVo(
    val proseListVo: List<ProseVo> = emptyList(),
    val allProseVo : ProseVo = ProseVo(),
    val homeViewType: HomeViewType = HomeViewType.TODAY_PROSE
)
