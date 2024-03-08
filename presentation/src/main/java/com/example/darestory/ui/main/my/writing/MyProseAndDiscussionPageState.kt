package com.example.darestory.ui.main.my.writing

import com.example.darestory.PageState
import com.example.domain.model.enums.DetailType
import com.example.domain.model.vo.ResultSearchVo
import kotlinx.coroutines.flow.StateFlow

data class MyProseAndDiscussionPageState(
    val type : StateFlow<DetailType>,
    val myWritingList : StateFlow<List<ResultSearchVo>>,
) : PageState