package com.example.darestory.ui.sign.signUpProfile

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.SignUpVo
import com.example.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpProfileViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<PageState.Default>() {

    var testEmail : String = ""
    var testPassword : String = ""

    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun onClickCompleteButton(){
        viewModelScope.launch {
            signUpUseCase(SignUpVo(testEmail, testPassword))
        }
    }

    fun updateTestInfo(email : String, password : String){
        testEmail = email
        testPassword = password
    }

}