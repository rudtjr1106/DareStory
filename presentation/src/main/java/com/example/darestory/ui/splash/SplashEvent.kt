package com.example.darestory.ui.splash

import com.example.darestory.Event

sealed class SplashEvent : Event {
    object GoToMainEvent : SplashEvent()
    object GoToLoginEvent : SplashEvent()
    data class ErrorServerEvent(val content : String) : SplashEvent()
    object ErrorVersionEvent : SplashEvent()
}
