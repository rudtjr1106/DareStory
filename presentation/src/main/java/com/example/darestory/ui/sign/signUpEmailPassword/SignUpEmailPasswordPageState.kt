package com.example.darestory.ui.sign.signUpEmailPassword

import com.example.darestory.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignUpEmailPasswordPageState(
    var email : MutableStateFlow<String>,
    var password : MutableStateFlow<String>,
    val isButtonEnable : StateFlow<Boolean>
) : PageState