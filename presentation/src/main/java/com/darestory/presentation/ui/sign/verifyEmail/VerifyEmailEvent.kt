package com.darestory.presentation.ui.sign.verifyEmail

import com.darestory.presentation.Event

sealed class VerifyEmailEvent : Event {
    data class GoToUrl(val url : String) : VerifyEmailEvent()
    object ErrorVerify : VerifyEmailEvent()
    object ErrorSendEmailEvent : VerifyEmailEvent()
    object GoToMain : VerifyEmailEvent()
}