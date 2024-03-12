package com.darestory.presentation.ui.main.my.writing

import com.darestory.presentation.Event

sealed class MyProseAndDiscussionEvent : Event {
    object GoToBackEvent : MyProseAndDiscussionEvent()
    object GoToProseWriteEvent : MyProseAndDiscussionEvent()
    object GoToDiscussionWriteEvent : MyProseAndDiscussionEvent()
}