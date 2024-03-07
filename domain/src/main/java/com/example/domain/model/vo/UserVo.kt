package com.example.domain.model.vo

data class UserVo(
    val age: String = "",
    val email: String = "",
    val gender: String = "",
    val myBook : HashMap<String, MyBookVo> = hashMapOf(),
    val myDiscussion : HashMap<String, DiscussionVo> = hashMapOf(),
    val myProse : HashMap<String, ProseVo> = hashMapOf(),
    val nickName : String = "",
    val readBook : HashMap<String, BookVo> = hashMapOf(),
    val userUid : String = ""
)
