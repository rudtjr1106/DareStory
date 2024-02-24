package com.example.domain.model

data class SignUpVo(
    val email : String = "",
    val password : String = "",
    val nickname : String = "",
    val age : String = "",
    val gender : String = ""
)