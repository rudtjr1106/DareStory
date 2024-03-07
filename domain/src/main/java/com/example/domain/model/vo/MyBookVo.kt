package com.example.domain.model.vo

data class MyBookVo(
    val myBookDescription : String = "",
    val myBookTitle : String = "",
    val prose : List<ProseVo> = emptyList(),
)
