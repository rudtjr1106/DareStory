package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.DetailType

data class DetailContentVo(
    val pageId : Int = -1,
    val pageType : DetailType = DetailType.PROSE,
    val author : String = "",
    val commentCount : Int = 0,
    val content : String = "",
    val createdAt : String = "",
    val likeCount : Int = 0,
    val title : String = "",
    var isLiked : Boolean = false
)
