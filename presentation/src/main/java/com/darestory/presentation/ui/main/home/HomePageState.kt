package com.darestory.presentation.ui.main.home

import com.darestory.presentation.PageState
import com.darestory.domain.model.enums.SortType
import com.darestory.domain.model.vo.HomeProseVo
import kotlinx.coroutines.flow.StateFlow

data class HomePageState(
    val proseList : StateFlow<List<HomeProseVo>>,
    val sortedType : StateFlow<SortType>
) : PageState