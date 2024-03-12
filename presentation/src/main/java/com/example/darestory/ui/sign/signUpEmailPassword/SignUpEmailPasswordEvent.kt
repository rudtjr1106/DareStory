package com.example.darestory.ui.sign.signUpEmailPassword

import com.example.darestory.Event

sealed class SignUpEmailPasswordEvent : Event {
    data class GoToNext(val email : String, val password : String) : SignUpEmailPasswordEvent()
    object GoBack : SignUpEmailPasswordEvent()
    object OnClickSpinner : SignUpEmailPasswordEvent()
    object ShowEmailDuplicateErrorTextEvent : SignUpEmailPasswordEvent()
}