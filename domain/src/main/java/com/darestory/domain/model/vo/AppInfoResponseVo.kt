package com.darestory.domain.model.vo

data class AppInfoResponseVo(
    val server : Boolean = true,
    val serverTime : String = "",
    val version : String = ""
)