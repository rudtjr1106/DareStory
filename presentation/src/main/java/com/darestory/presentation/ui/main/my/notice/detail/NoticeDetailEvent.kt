package com.darestory.presentation.ui.main.my.notice.detail

import com.darestory.presentation.Event

sealed class NoticeDetailEvent : Event{
    object GoToBackEvent : NoticeDetailEvent()
    object ShowWriterEvent : NoticeDetailEvent()
}