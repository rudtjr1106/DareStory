package com.example.darestory.ui.sign.signUpProfile

import com.example.darestory.Event
sealed class SignUpProfileEvent : Event {
    object GoBack : SignUpProfileEvent()
    object OnClickSpinner : SignUpProfileEvent()
}