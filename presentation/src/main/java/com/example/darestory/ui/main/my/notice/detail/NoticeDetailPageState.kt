package com.example.darestory.ui.main.my.notice.detail

import com.example.darestory.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class NoticeDetailPageState(
    var title : MutableStateFlow<String>,
    var createdAt : MutableStateFlow<String>,
    var content : MutableStateFlow<String>
): PageState