package com.darestory.presentation.ui.main.home.detail

import com.darestory.presentation.PageState
import com.darestory.domain.model.vo.DetailPageVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DetailPageState(
    val detailPageList: StateFlow<List<DetailPageVo>>,
    var commentEdit : MutableStateFlow<String>
) : PageState