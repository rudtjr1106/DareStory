package com.example.darestory.ui.main.search.result

import com.example.darestory.PageState
import com.example.domain.model.enums.SearchType
import com.example.domain.model.vo.ProseVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ResultSearchPageState(
    var searchContent : MutableStateFlow<String>,
    var searchType : MutableStateFlow<SearchType>,
    val searchResultProseList : StateFlow<List<ProseVo>>,
    val isResultIsEmpty : StateFlow<Boolean>,
    val searchContentIsEmpty : StateFlow<Boolean>
) : PageState