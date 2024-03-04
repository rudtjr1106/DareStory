package com.example.darestory.ui.main.search.result

import com.example.darestory.Event

sealed class ResultSearchEvent : Event{
    object GoBackEvent : ResultSearchEvent()
    object OnClickSpinnerEvent: ResultSearchEvent()
}