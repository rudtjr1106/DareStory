package com.example.darestory.ui.sign.certifyEmail

import com.example.darestory.Event

sealed class CertifyEmailEvent : Event {
    data class GoToUrl(val url : String) : CertifyEmailEvent()
    object ErrorCertify : CertifyEmailEvent()
    object GoToMain : CertifyEmailEvent()
}