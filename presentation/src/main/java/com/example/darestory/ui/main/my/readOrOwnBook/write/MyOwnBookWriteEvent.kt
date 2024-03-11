package com.example.darestory.ui.main.my.readOrOwnBook.write

import com.example.darestory.Event

sealed class MyOwnBookWriteEvent : Event {
    object OnClickBackEvent : MyOwnBookWriteEvent()
    object ToastEmptyTitleEvent : MyOwnBookWriteEvent()
    object ToastEmptyContentEvent : MyOwnBookWriteEvent()
    object ErrorUploadEvent : MyOwnBookWriteEvent()
}