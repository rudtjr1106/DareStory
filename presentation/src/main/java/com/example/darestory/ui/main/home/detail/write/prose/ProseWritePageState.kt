package com.example.darestory.ui.main.home.detail.write.prose

import com.example.darestory.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class ProseWritePageState(
    var title : MutableStateFlow<String>,
    var content : MutableStateFlow<String>,
    var authorSay : MutableStateFlow<String>
) : PageState
