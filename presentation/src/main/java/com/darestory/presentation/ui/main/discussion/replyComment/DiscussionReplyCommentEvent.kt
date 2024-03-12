package com.darestory.presentation.ui.main.discussion.replyComment

import com.darestory.presentation.Event
import com.darestory.domain.model.enums.BottomSheetType

sealed class DiscussionReplyCommentEvent : Event{
    object GoToBack: DiscussionReplyCommentEvent()
    data class ShowBottomSheetEvent(val type : BottomSheetType) : DiscussionReplyCommentEvent()
    object ShowCommentDeleteDialogEvent : DiscussionReplyCommentEvent()
    data class GoReportEvent(val who : String) : DiscussionReplyCommentEvent()
    object DeleteCommentErrorEvent : DiscussionReplyCommentEvent()
}