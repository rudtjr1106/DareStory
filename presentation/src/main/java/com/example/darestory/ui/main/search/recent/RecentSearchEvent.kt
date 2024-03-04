package com.example.darestory.ui.main.search.recent

import com.example.darestory.Event

sealed class RecentSearchEvent : Event{
    object GoBackEvent : RecentSearchEvent()
    data class GoResultEvent(val searchText : String) : RecentSearchEvent()
}