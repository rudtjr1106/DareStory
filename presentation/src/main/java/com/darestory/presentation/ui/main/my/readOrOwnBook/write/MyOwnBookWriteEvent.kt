package com.darestory.presentation.ui.main.my.readOrOwnBook.write

import com.darestory.presentation.Event

sealed class MyOwnBookWriteEvent : Event {
    object OnClickBackEvent : MyOwnBookWriteEvent()
    object ToastEmptyTitleEvent : MyOwnBookWriteEvent()
    object ToastEmptyContentEvent : MyOwnBookWriteEvent()
    object ErrorUploadEvent : MyOwnBookWriteEvent()
}