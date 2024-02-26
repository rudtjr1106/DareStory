package com.example.darestory.ui.sign.verifyEmail

import com.example.darestory.Event

sealed class VerifyEmailEvent : Event {
    data class GoToUrl(val url : String) : VerifyEmailEvent()
    object ErrorVerify : VerifyEmailEvent()
    object GoToMain : VerifyEmailEvent()
}