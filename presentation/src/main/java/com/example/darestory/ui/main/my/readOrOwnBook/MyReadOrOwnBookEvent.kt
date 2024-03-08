package com.example.darestory.ui.main.my.readOrOwnBook

import com.example.darestory.Event

sealed class MyReadOrOwnBookEvent : Event{
    object GoToBackEvent : MyReadOrOwnBookEvent()
}