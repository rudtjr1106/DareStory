package com.darestory.presentation.ui.main.search.recent

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RecentSearchPageState(
    var searchContent : MutableStateFlow<String>,
    val recentSearchedList : StateFlow<List<String>>,
    val searchContentIsEmpty : StateFlow<Boolean>
) : PageState