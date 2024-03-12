package com.darestory.presentation.ui.main.my.notice.detail

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class NoticeDetailPageState(
    var title : MutableStateFlow<String>,
    var createdAt : MutableStateFlow<String>,
    var content : MutableStateFlow<String>
): PageState