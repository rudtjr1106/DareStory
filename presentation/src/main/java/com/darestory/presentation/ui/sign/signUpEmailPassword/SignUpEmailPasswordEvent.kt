package com.darestory.presentation.ui.sign.signUpEmailPassword

import com.darestory.presentation.Event

sealed class SignUpEmailPasswordEvent : Event {
    data class GoToNext(val email : String, val password : String) : SignUpEmailPasswordEvent()
    object GoBack : SignUpEmailPasswordEvent()
    object OnClickSpinner : SignUpEmailPasswordEvent()
    data class ShowEmailDuplicateErrorTextEvent(val isDuplicate : Boolean) : SignUpEmailPasswordEvent()
}