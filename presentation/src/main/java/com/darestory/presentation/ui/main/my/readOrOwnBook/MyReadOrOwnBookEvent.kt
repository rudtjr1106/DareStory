package com.darestory.presentation.ui.main.my.readOrOwnBook

import com.darestory.presentation.Event

sealed class MyReadOrOwnBookEvent : Event{
    object GoToBackEvent : MyReadOrOwnBookEvent()
    object GoToResultSearchEvent : MyReadOrOwnBookEvent()
    object GoToMyOwnBookWriteEvent : MyReadOrOwnBookEvent()
    object ErrorUploadEvent : MyReadOrOwnBookEvent()
}