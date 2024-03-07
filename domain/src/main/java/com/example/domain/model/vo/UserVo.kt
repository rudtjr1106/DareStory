package com.example.domain.model.vo

data class UserVo(
    val age: String = "",
    val email: String = "",
    val gender: String = "",
    val myBook : HashMap<String, MyBookVo> = hashMapOf(),
    val myDiscussion : List<DiscussionVo> = emptyList(),
    val myProse : List<ProseVo> = emptyList(),
    val nickName : String = "",
    val readBook : HashMap<String, BookVo> = hashMapOf(),
    val userUid : String = ""
)
