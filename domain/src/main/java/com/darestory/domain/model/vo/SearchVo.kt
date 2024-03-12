package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.SearchType

data class SearchVo(
    val text : String = "",
    val type : SearchType = SearchType.TITLE
)