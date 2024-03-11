package com.example.darestory.ui.main.home.detail.wrtie

import com.example.darestory.Event

sealed class ProseWriteEvent : Event{
    object OnClickBackEvent : ProseWriteEvent()
    object SuccessUploadEvent : ProseWriteEvent()
    object ToastEmptyTitleEvent : ProseWriteEvent()
    object ToastEmptyContentEvent : ProseWriteEvent()
    object ErrorUploadEvent : ProseWriteEvent()
    object FocusEditTextEvent : ProseWriteEvent()
    object DeleteProseEvent : ProseWriteEvent()
}