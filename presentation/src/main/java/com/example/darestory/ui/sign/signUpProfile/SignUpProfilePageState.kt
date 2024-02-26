package com.example.darestory.ui.sign.signUpProfile

import com.example.darestory.PageState
import com.example.domain.model.enums.GenderType
import com.example.domain.model.error.NickNameError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignUpProfilePageState(
    val email : StateFlow<String>,
    val password : StateFlow<String>,
    var nickname : MutableStateFlow<String>,
    val nicknameError : StateFlow<NickNameError>,
    val age : MutableStateFlow<String>,
    val gender : StateFlow<GenderType>,
    val nicknameIsEmpty : StateFlow<Boolean>,
    val ageIsEmpty : StateFlow<Boolean>,
    val isCompleteButtonEnable : StateFlow<Boolean>
) : PageState