package com.example.darestory.ui.main.my

import com.example.darestory.Event
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.ReadOrOwnType

sealed class MyEvent : Event {

    object ShowLogoutDialogEvent : MyEvent()
    object ShowUnRegisterDialogEvent : MyEvent()
    data class GoToMyProseAndDiscussionEvent(val type : DetailType) : MyEvent()
    data class GoToMyReadOrOwnBookEvent(val type : ReadOrOwnType) : MyEvent()
    object GoToLoginEvent : MyEvent()
    object GoToNoticeEvent : MyEvent()
    object GoToSendEmailEvent : MyEvent()
    object GoToHomeEvent : MyEvent()
    object GoToDiscussionEvent : MyEvent()
    object ErrorMyInfoEvent : MyEvent()
    object GoToServiceNotionEvent : MyEvent()
    object GoToPrivateNotionEvent : MyEvent()
    object ShowAppVersionToastEvent : MyEvent()
}