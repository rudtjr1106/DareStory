package com.example.darestory.ui.main.home.detail

import com.example.darestory.Event
import com.example.domain.model.enums.DetailType

sealed class DetailEvent : Event {

    object GoToBack: DetailEvent()

}