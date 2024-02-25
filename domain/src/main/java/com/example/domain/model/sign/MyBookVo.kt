package com.example.domain.model.sign

data class MyBookVo(
    val explain : String? = "",
    val prose : HashMap<String, ProseVo>? = hashMapOf(),
    val title : String? = ""
)
