package com.example.darestory.ui.main.my.writing

import com.example.darestory.Event

sealed class MyProseAndDiscussionEvent : Event {
    object GoToBackEvent : MyProseAndDiscussionEvent()
}