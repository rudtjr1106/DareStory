package com.example.darestory.ui.main

import com.example.darestory.Event

sealed class MainEvent : Event {

    object NavigateHome: MainEvent()
    data class BottomNavigationVisibility(val isVisible:Boolean) : MainEvent()
    object NavigateDiscussion: MainEvent()
    object NavigateMy: MainEvent()

}
