package com.example.domain.model.vo

data class UserVo(
    val age: String = "",
    val email: String = "",
    val gender: String = "",
    val myBook : HashMap<String, MyBookVo> = hashMapOf(),
    val nickName : String = "",
    val readBook : HashMap<String, BookVo> = hashMapOf(),
    val userUid : String = ""
)
