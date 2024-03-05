package com.example.darestory.ui.main.discussion.write

import com.example.darestory.Event

sealed class DiscussionWriteEvent : Event {
    object OnClickBackEvent : DiscussionWriteEvent()
    object SuccessUploadEvent : DiscussionWriteEvent()
    object ToastEmptyTitleEvent : DiscussionWriteEvent()
    object ToastEmptyContentEvent : DiscussionWriteEvent()
    object ToastEmptyBookEvent : DiscussionWriteEvent()
    object GoToBookSearch : DiscussionWriteEvent()
    object SuccessGetDiscussionEvent : DiscussionWriteEvent()
}