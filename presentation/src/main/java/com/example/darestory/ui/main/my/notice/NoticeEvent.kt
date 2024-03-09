package com.example.darestory.ui.main.my.notice

import com.example.darestory.Event

sealed class NoticeEvent : Event{
    object GoToBackEvent : NoticeEvent()
}