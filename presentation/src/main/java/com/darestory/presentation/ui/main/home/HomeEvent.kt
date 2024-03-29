package com.darestory.presentation.ui.main.home

import com.darestory.presentation.Event
import com.darestory.domain.model.enums.DetailType

sealed class HomeEvent : Event {

    data class GoToDetailPageEvent(val proseId : Int, val proseType : DetailType): HomeEvent()
    object GoToWriteProseEvent : HomeEvent()
    object GoToRecentSearchPageEvent : HomeEvent()
    object ScrollUpEvent : HomeEvent()
    object GoToDiscussionEvent : HomeEvent()

    object GoToMyEvent : HomeEvent()

}