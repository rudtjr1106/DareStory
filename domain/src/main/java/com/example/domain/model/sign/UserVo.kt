package com.example.domain.model.sign

data class UserVo(
    val age: String? = "",
    val email: String = "",
    val gender: String? = "",
    val myBook: HashMap<String, MyBookVo>? = hashMapOf(),
    val myBookDis: HashMap<String, BookDisVo>? = hashMapOf(),
    val myProse : HashMap<String, ProseVo>? = hashMapOf(),
    val nickName : String? = "",
    val password : String = "",
    val userUid : String? = ""
)
