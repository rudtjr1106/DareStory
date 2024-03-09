package com.example.darestory.ui.main.my.notice

import com.example.darestory.PageState
import com.example.domain.model.vo.NoticeVo
import kotlinx.coroutines.flow.StateFlow

data class NoticePageState(
    val noticeList : StateFlow<List<NoticeVo>>
) : PageState