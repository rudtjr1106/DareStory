package com.example.darestory.ui.sign.signUpEmailPassword

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.SignUpVo
import com.example.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEmailPasswordViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) :
    BaseViewModel<SignUpEmailPasswordPageState>() {

    private val emailStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val passwordStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val isButtonEnableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SignUpEmailPasswordPageState = SignUpEmailPasswordPageState(
        emailStateFlow,
        passwordStateFlow,
        isButtonEnableStateFlow.asStateFlow()
    )

    fun onTextChangedAfter(){
        var flag = uiState.email.value.isNotEmpty() && uiState.password.value.isNotEmpty()
        updateNextButton(flag)
    }

    fun onClickNextButton(){
        emitEventFlow(SignUpEmailPasswordEvent.GoToNext(uiState.email.value, uiState.password.value))
    }

    fun onClickBackButton(){
        emitEventFlow(SignUpEmailPasswordEvent.GoBack)
    }

    private fun updateNextButton(isEnable : Boolean){
        viewModelScope.launch {
            isButtonEnableStateFlow.update { isEnable }
            signUpUseCase(SignUpVo("sk", "asd"))
        }
    }
}