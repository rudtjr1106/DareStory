package com.darestory.presentation.ui.splash

import com.darestory.presentation.Event

sealed class SplashEvent : Event {
    object GoToMainEvent : SplashEvent()
    object GoToLoginEvent : SplashEvent()
    data class ErrorServerEvent(val content : String) : SplashEvent()
    object ErrorVersionEvent : SplashEvent()
}
