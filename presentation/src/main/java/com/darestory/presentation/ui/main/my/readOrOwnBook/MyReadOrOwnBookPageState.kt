package com.darestory.presentation.ui.main.my.readOrOwnBook

import com.darestory.presentation.PageState
import com.darestory.domain.model.enums.ReadOrOwnType
import com.darestory.domain.model.vo.MyReadOrOwnBookVo
import kotlinx.coroutines.flow.StateFlow

data class MyReadOrOwnBookPageState(
    val type : StateFlow<ReadOrOwnType>,
    val myReadOrOwnBookList : StateFlow<List<MyReadOrOwnBookVo>>,
) : PageState