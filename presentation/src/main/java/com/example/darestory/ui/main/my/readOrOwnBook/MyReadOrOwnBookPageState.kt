package com.example.darestory.ui.main.my.readOrOwnBook

import com.example.darestory.PageState
import com.example.domain.model.enums.ReadOrOwnType
import com.example.domain.model.vo.MyReadOrOwnBookVo
import kotlinx.coroutines.flow.StateFlow

data class MyReadOrOwnBookPageState(
    val type : StateFlow<ReadOrOwnType>,
    val myReadOrOwnBookList : StateFlow<List<MyReadOrOwnBookVo>>,
) : PageState