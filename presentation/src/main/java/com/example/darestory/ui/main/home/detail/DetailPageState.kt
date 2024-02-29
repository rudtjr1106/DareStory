package com.example.darestory.ui.main.home.detail

import com.example.darestory.PageState
import com.example.domain.model.vo.DetailPageVo
import com.example.domain.model.vo.ProseVo
import kotlinx.coroutines.flow.StateFlow

data class DetailPageState(
    val detailPageList: StateFlow<List<DetailPageVo>>
) : PageState