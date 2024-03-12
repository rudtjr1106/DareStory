package com.darestory.presentation.ui.main.my

import com.darestory.presentation.Event
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.enums.ReadOrOwnType

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