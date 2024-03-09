package com.example.darestory.ui.main.my.readOrOwnBook.write

import com.example.darestory.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class MyOwnBookWritePageState(
    var title : MutableStateFlow<String>,
    var content : MutableStateFlow<String>,
) : PageState