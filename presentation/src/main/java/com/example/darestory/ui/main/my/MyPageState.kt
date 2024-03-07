package com.example.darestory.ui.main.my

import com.example.darestory.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class MyPageState(
    var nickname : MutableStateFlow<String>,
    var myProseCount : MutableStateFlow<String>,
    var myDiscussionCount : MutableStateFlow<String>,
) : PageState