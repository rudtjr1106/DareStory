package com.darestory.presentation.ui.main.my.readOrOwnBook.write

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class MyOwnBookWritePageState(
    var title : MutableStateFlow<String>,
    var content : MutableStateFlow<String>,
) : PageState