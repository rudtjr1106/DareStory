package com.example.darestory.ui.sign.login

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.UserInfo
import com.example.domain.model.vo.LoginVo
import com.example.domain.usecase.sign.CheckAutoLoginUseCase
import com.example.domain.usecase.sign.LoginUseCase
import com.example.domain.usecase.sign.SendPasswordResetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sendPasswordResetUseCase: SendPasswordResetUseCase,
    private val checkAutoLoginUseCase: CheckAutoLoginUseCase
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
            if(loginUseCase(LoginVo(email = emailStateFlow.value, password = passwordStateFlow.value))) goToMain()
            else showLoginErrorToast()
        }
    }

    private fun goToMain(){
        viewModelScope.launch {
            val result = checkAutoLoginUseCase(Unit)
            UserInfo.updateInfo(result)
        }
        emitEventFlow(LoginEvent.GoToMainEvent)
    }

    private fun showLoginErrorToast(){
        emitEventFlow(LoginEvent.ShowLoginErrorToastEvent)
    }

    fun onFindPasswordTextClick(){
        emitEventFlow(LoginEvent.FindPasswordTextClickEvent)
    }

    fun sendResetPasswordEmail(email : String){
        viewModelScope.launch {
            if(sendPasswordResetUseCase(email)) showSuccessSendPwToast()
            else showErrorSendPwToast()
        }
    }

    private fun showSuccessSendPwToast(){
        emitEventFlow(LoginEvent.ShowSuccessSendPwToastEvent)
    }

    private fun showErrorSendPwToast(){
        emitEventFlow(LoginEvent.ShowErrorSendPwToastEvent)
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