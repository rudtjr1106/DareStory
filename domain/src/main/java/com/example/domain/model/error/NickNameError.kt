package com.example.domain.model.error

sealed class NickNameError{
    object LengthError : NickNameError()
    object DuplicateError : NickNameError()
    object Nothing : NickNameError()
    object NoError : NickNameError()
}
