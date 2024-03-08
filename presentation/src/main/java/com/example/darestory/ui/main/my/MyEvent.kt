package com.example.darestory.ui.main.my

import com.example.darestory.Event
import com.example.domain.model.enums.DetailType

sealed class MyEvent : Event {
    data class GoToMyProseAndDiscussion(val type : DetailType) : MyEvent()
}