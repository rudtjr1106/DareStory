package com.example.domain.model.vo

import com.example.domain.model.enums.DetailType

data class RecentSearchVo(
    val type : DetailType = DetailType.PROSE,
    val text : String = "",
    val saveTime : Long = 0,
)