package com.example.darestory.ui.main.discussion

import com.example.darestory.Event

sealed class DiscussionEvent : Event {
    object ScrollUpEvent : DiscussionEvent()
    object GoToHomeEvent : DiscussionEvent()
    object GoToMyEvent : DiscussionEvent()
}