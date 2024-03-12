package com.darestory.presentation.ui.sign.login

import com.darestory.presentation.Event

sealed class LoginEvent : Event {
    object GoToMainEvent : LoginEvent()
    object ShowLoginErrorToastEvent : LoginEvent()
    object SignUpTextClickEvent : LoginEvent()
    object FindPasswordTextClickEvent : LoginEvent()
    object ShowSuccessSendPwToastEvent : LoginEvent()
    object ShowErrorSendPwToastEvent : LoginEvent()
}
