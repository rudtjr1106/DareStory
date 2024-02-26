package com.example.darestory.ui.sign.login

import com.example.darestory.Event

sealed class LoginEvent : Event {
    object GoToMainEvent : LoginEvent()
    object ShowLoginErrorToastEvent : LoginEvent()
    object SignUpTextClickEvent : LoginEvent()
    object FindPasswordTextClickEvent : LoginEvent()
    object ShowSuccessSendPwToastEvent : LoginEvent()
    object ShowErrorSendPwToastEvent : LoginEvent()
}
