package com.example.darestory.ui.sign.signUpEmailPassword

import com.example.darestory.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignUpEmailPasswordPageState(
    var email : MutableStateFlow<String>,
    var emailDomain : MutableStateFlow<String>,
    var password : MutableStateFlow<String>,
    val emailIsEmpty : StateFlow<Boolean>,
    val emailDomainIsEmpty : StateFlow<Boolean>,
    val passwordIsEmpty : StateFlow<Boolean>,
    val isButtonEnable : StateFlow<Boolean>
) : PageState