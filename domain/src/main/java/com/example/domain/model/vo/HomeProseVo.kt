package com.example.domain.model.vo

import com.example.domain.model.enums.HomeViewType

data class HomeProseVo(
    val proseListVo: List<ProseVo> = emptyList(),
    val homeViewType: HomeViewType = HomeViewType.TODAY_PROSE
)
