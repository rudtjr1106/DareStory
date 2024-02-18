package com.example.darestory.ui.sign.signUpProfile

import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpProfileViewModel @Inject constructor(
) : BaseViewModel<PageState.Default>() {

    var testEmail : String = ""
    var testPassword : String = ""

    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun onClickCompleteButton(){
//        viewModelScope.launch {
//            signUpUseCase(SignUpVo(testEmail, testPassword))
//        }
//        DareLog.D("나 버튼")
    }

    fun updateTestInfo(email : String, password : String){
        testEmail = email
        testPassword = password
    }

}