package com.example.darestory.ui.sign.signUpEmailPassword

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.DareLog
import com.example.domain.usecase.GetAllEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEmailPasswordViewModel @Inject constructor(
    private val getAllEmailUseCase: GetAllEmailUseCase
) :
    BaseViewModel<SignUpEmailPasswordPageState>() {

    private val emailStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val emailDomainStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isEmailDuplicateStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val passwordStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val emailIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val emailDomainIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val passwordIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val isButtonEnableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SignUpEmailPasswordPageState = SignUpEmailPasswordPageState(
        emailStateFlow,
        emailDomainStateFlow,
        isEmailDuplicateStateFlow.asStateFlow(),
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

    private lateinit var emailList : List<String>

    fun getAllEmail(){
        viewModelScope.launch {
            emailList = getAllEmailUseCase(Unit)
        }
    }

    fun onEmailTextChangedAfter(){
        viewModelScope.launch {
            emailIsEmptyStateFlow.update { emailStateFlow.value.isEmpty() }
            checkEmailDuplicate()
            updateNextButton()
        }
    }

    private fun checkEmailDuplicate(){
        val email = emailStateFlow.value + DIVIDE_EMAIL + emailDomainStateFlow.value
        viewModelScope.launch {
            isEmailDuplicateStateFlow.update { emailList.contains(email) }
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
            checkEmailDuplicate()
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
                !passwordIsEmptyStateFlow.value && passwordStateFlow.value.length >= PASSWORD_TERMS && !isEmailDuplicateStateFlow.value
        viewModelScope.launch {
            isButtonEnableStateFlow.update { enableTerms }
        }
    }
}