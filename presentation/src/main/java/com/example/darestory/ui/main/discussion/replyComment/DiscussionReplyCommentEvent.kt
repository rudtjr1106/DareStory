package com.example.darestory.ui.main.discussion.replyComment

import com.example.darestory.Event
import com.example.domain.model.enums.BottomSheetType

sealed class DiscussionReplyCommentEvent : Event{
    object GoToBack: DiscussionReplyCommentEvent()
    data class ShowBottomSheetEvent(val type : BottomSheetType) : DiscussionReplyCommentEvent()
    object ShowCommentDeleteDialogEvent : DiscussionReplyCommentEvent()
    data class GoReportEvent(val who : String) : DiscussionReplyCommentEvent()
}