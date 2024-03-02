package com.example.darestory.ui.main.home.detail.write.prose

import com.example.darestory.Event

sealed class ProseWriteEvent : Event{
    object OnClickBack : ProseWriteEvent()
    object SuccessUpload : ProseWriteEvent()
}