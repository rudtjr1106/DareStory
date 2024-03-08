package com.example.domain.model.vo

data class AddMyOwnBookProseRequestVo(
    val title : String = "",
    val proseVo: ProseVo = ProseVo()
)