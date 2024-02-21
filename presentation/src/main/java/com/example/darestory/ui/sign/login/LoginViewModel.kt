package com.example.darestory.ui.sign.login

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.darestory.ui.sign.signUpEmailPassword.SignUpEmailPasswordPageState
import com.example.domain.model.LoginVo
import com.example.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginPageState>() {

    private val emailStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val passwordStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val emailIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val passwordIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val uiState: LoginPageState = LoginPageState(
        emailStateFlow,
        passwordStateFlow,
        emailIsEmptyStateFlow.asStateFlow(),
        passwordIsEmptyStateFlow.asStateFlow()
    )

    fun onLoginButtonClick(){
        viewModelScope.launch {
            loginUseCase(LoginVo(email = emailStateFlow.value, password = passwordStateFlow.value))
        }
    }

    fun onFindPasswordTextClick(){
        emitEventFlow(LoginEvent.FindPasswordTextClickEvent)
    }

    fun onSignUpTextClick(){
        emitEventFlow(LoginEvent.SignUpTextClickEvent)
    }

    fun onEmailChangedAfter(){
        viewModelScope.launch {
            emailIsEmptyStateFlow.update { emailStateFlow.value.isEmpty() }
        }
    }

    fun onPasswordChangedAfter(){
        viewModelScope.launch {
            passwordIsEmptyStateFlow.update { passwordStateFlow.value.isEmpty() }
        }
    }

    fun onClickEmailResetButton(){
        viewModelScope.launch {
            emailStateFlow.update { "" }
            onEmailChangedAfter()
        }
    }

    fun onClickPasswordResetButton(){
        viewModelScope.launch {
            passwordStateFlow.update { "" }
            onPasswordChangedAfter()
        }
    }


}