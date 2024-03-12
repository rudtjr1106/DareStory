package com.darestory.presentation.ui.main.my.notice

import com.darestory.presentation.PageState
import com.darestory.domain.model.vo.NoticeVo
import kotlinx.coroutines.flow.StateFlow

data class NoticePageState(
    val noticeList : StateFlow<List<NoticeVo>>
) : PageState