package com.darestory.presentation.ui.sign.signUpEmailPassword

import com.darestory.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignUpEmailPasswordPageState(
    var email : MutableStateFlow<String>,
    var emailDomain : MutableStateFlow<String>,
    val isEmailDuplicate : StateFlow<Boolean>,
    var password : MutableStateFlow<String>,
    val emailIsEmpty : StateFlow<Boolean>,
    val emailDomainIsEmpty : StateFlow<Boolean>,
    val passwordIsEmpty : StateFlow<Boolean>,
    val isButtonEnable : StateFlow<Boolean>
) : PageState