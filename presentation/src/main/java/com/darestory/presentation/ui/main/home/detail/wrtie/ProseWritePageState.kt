package com.darestory.presentation.ui.main.home.detail.wrtie

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class ProseWritePageState(
    var title : MutableStateFlow<String>,
    var content : MutableStateFlow<String>,
    var authorSay : MutableStateFlow<String>
) : PageState
