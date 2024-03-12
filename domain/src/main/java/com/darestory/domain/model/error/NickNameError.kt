package com.darestory.domain.model.error

sealed class NickNameError{
    object LengthError : NickNameError()
    object DuplicateError : NickNameError()
    object NoError : NickNameError()
}
