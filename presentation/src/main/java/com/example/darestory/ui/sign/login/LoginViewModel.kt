package com.example.darestory.ui.sign.login

import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.DareLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {


    fun onLoginButtonClick(){
        DareLog.D("클릭됨")
        emitEventFlow(LoginEvent.GoSignUpEvent)
    }

    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")
}