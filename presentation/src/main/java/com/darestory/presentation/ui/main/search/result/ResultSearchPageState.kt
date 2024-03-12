package com.darestory.presentation.ui.main.search.result

import com.darestory.presentation.PageState
import com.darestory.domain.model.enums.SearchType
import com.darestory.domain.model.vo.ResultSearchVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ResultSearchPageState(
    var searchContent : MutableStateFlow<String>,
    var searchType : MutableStateFlow<SearchType>,
    val searchResultList : StateFlow<List<ResultSearchVo>>,
    val isResultIsEmpty : StateFlow<Boolean>,
    val searchContentIsEmpty : StateFlow<Boolean>
) : PageState