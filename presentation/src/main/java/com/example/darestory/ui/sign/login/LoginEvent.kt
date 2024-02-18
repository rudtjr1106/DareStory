package com.example.darestory.ui.sign.login

import com.example.darestory.Event

sealed class LoginEvent : Event {
    object GoSignUpEvent : LoginEvent()
}
