package com.darestory.presentation.ui.main.discussion.write

import com.darestory.presentation.Event

sealed class DiscussionWriteEvent : Event {
    object OnClickBackEvent : DiscussionWriteEvent()
    object SuccessUploadEvent : DiscussionWriteEvent()
    object ToastEmptyTitleEvent : DiscussionWriteEvent()
    object ToastEmptyContentEvent : DiscussionWriteEvent()
    object ToastEmptyBookEvent : DiscussionWriteEvent()
    object GoToBookSearch : DiscussionWriteEvent()
    object SuccessGetDiscussionEvent : DiscussionWriteEvent()
    object DeleteDiscussionEvent : DiscussionWriteEvent()
    object ErrorUploadEvent : DiscussionWriteEvent()
}