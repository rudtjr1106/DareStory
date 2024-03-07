package com.example.darestory.ui.main.home

import com.example.darestory.Event
import com.example.domain.model.enums.DetailType

sealed class HomeEvent : Event {

    data class GoToDetailPageEvent(val proseId : Int, val proseType : DetailType): HomeEvent()
    object GoToWriteProseEvent : HomeEvent()
    object GoToRecentSearchPageEvent : HomeEvent()
    object ScrollUpEvent : HomeEvent()
    object GoToDiscussionEvent : HomeEvent()

    object GoToMyEvent : HomeEvent()

}