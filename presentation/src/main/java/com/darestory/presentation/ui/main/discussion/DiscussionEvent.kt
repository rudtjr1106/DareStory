package com.darestory.presentation.ui.main.discussion

import com.darestory.presentation.Event

sealed class DiscussionEvent : Event {
    object ScrollUpEvent : DiscussionEvent()
    object GoToHomeEvent : DiscussionEvent()
    object GoToMyEvent : DiscussionEvent()
}