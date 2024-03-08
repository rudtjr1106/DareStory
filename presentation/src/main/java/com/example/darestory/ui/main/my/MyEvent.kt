package com.example.darestory.ui.main.my

import com.example.darestory.Event
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.ReadOrOwnType

sealed class MyEvent : Event {
    data class GoToMyProseAndDiscussionEvent(val type : DetailType) : MyEvent()
    data class GoToMyReadOrOwnBookEvent(val type : ReadOrOwnType) : MyEvent()
}