package com.example.darestory.ui.sign.login

import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun onLoginButtonClick(){
        emitEventFlow(LoginEvent.LoginButtonClickEvent)
    }

    fun onFindPasswordTextClick(){
        emitEventFlow(LoginEvent.FindPasswordTextClickEvent)
    }

    fun onSignUpTextClick(){
        emitEventFlow(LoginEvent.SignUpTextClickEvent)
    }

}