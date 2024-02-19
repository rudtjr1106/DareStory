package com.example.darestory.ui.sign.login

import com.example.darestory.Event

sealed class LoginEvent : Event {

    object LoginButtonClickEvent : LoginEvent()
    object SignUpTextClickEvent : LoginEvent()
    object FindPasswordTextClickEvent : LoginEvent()
}
