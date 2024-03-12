package com.darestory.presentation.ui.main.home.detail

import com.darestory.presentation.Event
import com.darestory.domain.model.enums.BottomSheetType

sealed class DetailEvent : Event {

    object GoToBack: DetailEvent()
    data class ShowBottomSheetEvent(val type : BottomSheetType) : DetailEvent()
    object GoEditEvent : DetailEvent()
    object ShowDeleteDialogEvent : DetailEvent()
    object ShowCommentDeleteDialogEvent : DetailEvent()
    data class GoReportEvent(val who : String) : DetailEvent()
    object GoToMyOwnBookSelectEvent : DetailEvent()
    object DeleteProseErrorEvent : DetailEvent()
    object DeleteDiscussionErrorEvent : DetailEvent()

}