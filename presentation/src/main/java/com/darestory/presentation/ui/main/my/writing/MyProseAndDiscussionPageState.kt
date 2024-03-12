package com.darestory.presentation.ui.main.my.writing

import com.darestory.presentation.PageState
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.vo.ResultSearchVo
import kotlinx.coroutines.flow.StateFlow

data class MyProseAndDiscussionPageState(
    val type : StateFlow<DetailType>,
    val myWritingList : StateFlow<List<ResultSearchVo>>,
) : PageState