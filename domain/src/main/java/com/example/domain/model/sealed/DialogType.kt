package com.example.domain.model.sealed

sealed class DialogType {
    object TextTwoButton : DialogType()
    object TextOneButton : DialogType()
    object EditTextTwoButton : DialogType()
    object IconOneButton : DialogType()
}