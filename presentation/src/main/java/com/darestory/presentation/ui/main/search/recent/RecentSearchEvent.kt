package com.darestory.presentation.ui.main.search.recent

import com.darestory.presentation.Event

sealed class RecentSearchEvent : Event{
    object GoBackEvent : RecentSearchEvent()
    data class GoResultEvent(val searchText : String) : RecentSearchEvent()
}