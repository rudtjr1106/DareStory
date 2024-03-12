package com.darestory.presentation.ui.main.my.notice

import com.darestory.presentation.Event

sealed class NoticeEvent : Event{
    object GoToBackEvent : NoticeEvent()
}