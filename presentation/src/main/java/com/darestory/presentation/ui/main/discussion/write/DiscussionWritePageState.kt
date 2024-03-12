package com.darestory.presentation.ui.main.discussion.write

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DiscussionWritePageState(
    var title : MutableStateFlow<String>,
    var content : MutableStateFlow<String>,
    val isBookSelected : StateFlow<Boolean>,
) : PageState