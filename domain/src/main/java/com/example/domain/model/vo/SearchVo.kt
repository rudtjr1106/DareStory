package com.example.domain.model.vo

import com.example.domain.model.enums.SearchType

data class SearchVo(
    val text : String = "",
    val type : SearchType = SearchType.TITLE
)