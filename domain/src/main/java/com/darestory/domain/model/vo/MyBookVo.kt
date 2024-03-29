package com.darestory.domain.model.vo

data class MyBookVo(
    val myBookDescription : String = "",
    val myBookTitle : String = "",
    val prose : HashMap<String, ProseVo> = hashMapOf(),
)
