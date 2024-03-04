package com.example.darestory.ui.main.home

import com.example.darestory.PageState
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.HomeProseVo
import kotlinx.coroutines.flow.StateFlow

data class HomePageState(
    val proseList : StateFlow<List<HomeProseVo>>,
    val sortedType : StateFlow<SortType>
) : PageState