package com.example.darestory.ui.main.home.detail.write.discussion

import com.example.darestory.Event

sealed class DiscussionWriteEvent : Event {
    object OnClickBackEvent : DiscussionWriteEvent()
    object SuccessUploadEvent : DiscussionWriteEvent()
    object ToastEmptyTitleEvent : DiscussionWriteEvent()
    object ToastEmptyContentEvent : DiscussionWriteEvent()
    object ToastEmptyBookEvent : DiscussionWriteEvent()
}