package com.darestory.presentation.ui.main.home.detail.wrtie

import com.darestory.presentation.Event

sealed class ProseWriteEvent : Event{
    object OnClickBackEvent : ProseWriteEvent()
    object SuccessUploadEvent : ProseWriteEvent()
    object ToastEmptyTitleEvent : ProseWriteEvent()
    object ToastEmptyContentEvent : ProseWriteEvent()
    object ErrorUploadEvent : ProseWriteEvent()
    object FocusEditTextEvent : ProseWriteEvent()
    object DeleteProseEvent : ProseWriteEvent()
}