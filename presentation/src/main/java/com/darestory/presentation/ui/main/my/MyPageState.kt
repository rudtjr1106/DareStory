package com.darestory.presentation.ui.main.my

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class MyPageState(
    var nickname : MutableStateFlow<String>,
    var myProseCount : MutableStateFlow<String>,
    var myDiscussionCount : MutableStateFlow<String>,
) : PageState