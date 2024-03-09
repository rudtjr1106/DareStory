package com.example.darestory.ui.main.my.notice.detail

import com.example.darestory.Event

sealed class NoticeDetailEvent : Event{
    object GoToBackEvent : NoticeDetailEvent()
    object ShowWriterEvent : NoticeDetailEvent()
}