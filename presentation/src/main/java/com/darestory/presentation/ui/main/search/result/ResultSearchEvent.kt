package com.darestory.presentation.ui.main.search.result

import com.darestory.presentation.Event

sealed class ResultSearchEvent : Event{
    object GoBackEvent : ResultSearchEvent()
    object OnClickSpinnerEvent: ResultSearchEvent()
}