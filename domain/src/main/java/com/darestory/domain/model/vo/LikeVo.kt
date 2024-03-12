package com.darestory.domain.model.vo

data class LikeVo(
    val pageId : Int = -1,
    val nickName : String = "",
    val isLiked : Boolean = false
)