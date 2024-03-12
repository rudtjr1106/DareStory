package com.darestory.presentation.ui.sign.signUpProfile

import com.darestory.presentation.Event
sealed class SignUpProfileEvent : Event {
    object GoBack : SignUpProfileEvent()
    object OnClickSpinner : SignUpProfileEvent()
    data class GoCertifyEmail(val email : String) : SignUpProfileEvent()
}