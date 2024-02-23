package com.example.darestory.ui.sign.signUpEmailPassword

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEmailPasswordViewModel @Inject constructor() :
    BaseViewModel<SignUpEmailPasswordPageState>() {

    private val emailStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val emailDomainStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val passwordStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val emailIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val emailDomainIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val passwordIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val isButtonEnableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SignUpEmailPasswordPageState = SignUpEmailPasswordPageState(
        emailStateFlow,
        emailDomainStateFlow,
        passwordStateFlow,
        emailIsEmptyStateFlow.asStateFlow(),
        emailDomainIsEmptyStateFlow.asStateFlow(),
        passwordIsEmptyStateFlow.asStateFlow(),
        isButtonEnableStateFlow.asStateFlow()
    )

    companion object{
        const val DIVIDE_EMAIL = "@"
        const val PASSWORD_TERMS = 8
    }

    fun onEmailTextChangedAfter(){
        viewModelScope.launch {
            emailIsEmptyStateFlow.update { emailStateFlow.value.isEmpty() }
            updateNextButton()
        }
    }

    fun onPasswordTextChangedAfter(){
        viewModelScope.launch {
            passwordIsEmptyStateFlow.update { passwordStateFlow.value.isEmpty() }
            updateNextButton()
        }
    }

    fun onClickSpinner(){
        emitEventFlow(SignUpEmailPasswordEvent.OnClickSpinner)
    }

    fun onSelectedEmailDomain(domain : String){
        viewModelScope.launch {
            emailDomainStateFlow.update { domain }
            onEmailDomainChangedAfter()
        }
    }

    private fun onEmailDomainChangedAfter(){
        viewModelScope.launch {
            emailDomainIsEmptyStateFlow.update { emailDomainStateFlow.value.isEmpty() }
            updateNextButton()
        }
    }

    fun onClickNextButton(){
        val email = emailStateFlow.value + DIVIDE_EMAIL + emailDomainStateFlow.value
        emitEventFlow(SignUpEmailPasswordEvent.GoToNext(email, passwordStateFlow.value))
    }

    fun onClickBackButton(){
        emitEventFlow(SignUpEmailPasswordEvent.GoBack)
    }

    fun onClickResetPasswordButton(){
        viewModelScope.launch {
            passwordStateFlow.update { "" }
            onPasswordTextChangedAfter()
        }
    }

    private fun updateNextButton(){
        val enableTerms = !emailIsEmptyStateFlow.value && !emailDomainIsEmptyStateFlow.value &&
                !passwordIsEmptyStateFlow.value && passwordStateFlow.value.length >= PASSWORD_TERMS
        viewModelScope.launch {
            isButtonEnableStateFlow.update { enableTerms }
        }
    }
}