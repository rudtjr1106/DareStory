package com.example.domain.model.vo

data class MyBookVo(
    val explain : String = "",
    val prose : HashMap<String, ProseVo> = hashMapOf(),
    val title : String = ""
)
