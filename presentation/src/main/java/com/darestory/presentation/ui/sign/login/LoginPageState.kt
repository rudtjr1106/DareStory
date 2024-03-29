package com.darestory.presentation.ui.sign.login

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginPageState(
    var email : MutableStateFlow<String>,
    var password : MutableStateFlow<String>,
    val emailIsEmpty : StateFlow<Boolean>,
    val passwordIsEmpty : StateFlow<Boolean>
) : PageState