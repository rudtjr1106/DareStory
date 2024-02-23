package com.example.darestory.ui.sign.signUpProfile

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.darestory.ui.sign.signUpEmailPassword.SignUpEmailPasswordPageState
import com.example.darestory.util.DareLog
import com.example.domain.model.SignUpVo
import com.example.domain.model.enums.GenderType
import com.example.domain.model.error.NickNameError
import com.example.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpProfileViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<SignUpProfilePageState>() {

    companion object{
        const val MIN_NICKNAME = 2
        const val MAX_NICKNAME = 10
    }


    private val emailStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val passwordStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val nicknameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val nicknameErrorStateFlow: MutableStateFlow<NickNameError> = MutableStateFlow(NickNameError.Nothing)
    private val ageStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val genderStateFlow: MutableStateFlow<GenderType> = MutableStateFlow(GenderType.NOTHING)
    private val nicknameIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val ageIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val isCompleteButtonEnableStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SignUpProfilePageState = SignUpProfilePageState(
        emailStateFlow.asStateFlow(),
        passwordStateFlow.asStateFlow(),
        nicknameStateFlow,
        nicknameErrorStateFlow.asStateFlow(),
        ageStateFlow,
        genderStateFlow.asStateFlow(),
        nicknameIsEmptyStateFlow.asStateFlow(),
        ageIsEmptyStateFlow.asStateFlow(),
        isCompleteButtonEnableStateFlow.asStateFlow()
    )

    fun onClickBackButton(){
        emitEventFlow(SignUpProfileEvent.GoBack)
    }

    fun onNickNameTextChangedAfter(){
        viewModelScope.launch {
            nicknameIsEmptyStateFlow.update { nicknameStateFlow.value.isEmpty() }
            checkNickNameState(nicknameStateFlow.value)
            updateButtonState()
        }
    }

    private fun checkNickNameState(nickname : String){
        val lengthTerms = nickname.length < MIN_NICKNAME || nickname.length > MAX_NICKNAME
        if(lengthTerms) updateNickNameState(NickNameError.LengthError)
        else updateNickNameState(NickNameError.NoError)
        //닉네임 중복에러 해야됨

    }

    private fun updateNickNameState(state : NickNameError){
        viewModelScope.launch {
            nicknameErrorStateFlow.update { state }
        }
    }

    fun onClickAgeSpinner(){
        emitEventFlow(SignUpProfileEvent.OnClickSpinner)
    }

    fun onSelectedAge(age : String){
        viewModelScope.launch {
            ageStateFlow.update { age }
            onAgeTextChangedAfter()
        }
    }

    private fun onAgeTextChangedAfter(){
        viewModelScope.launch {
            ageIsEmptyStateFlow.update { ageStateFlow.value.isEmpty() }
            updateButtonState()
        }
    }

    fun onClickGenderTextButton(type : GenderType){
        viewModelScope.launch {
            genderStateFlow.update { type }
            updateButtonState()
        }
    }

    private fun updateButtonState(){
        val canUse = !nicknameIsEmptyStateFlow.value && !ageIsEmptyStateFlow.value && (genderStateFlow.value != GenderType.NOTHING)
        viewModelScope.launch {
            isCompleteButtonEnableStateFlow.update { canUse }
        }
    }

    fun onClickCompleteButton(){
        viewModelScope.launch {
            signUpUseCase(SignUpVo(emailStateFlow.value, passwordStateFlow.value))
        }
    }

    fun updateTestInfo(email : String, password : String){
        viewModelScope.launch {
            emailStateFlow.update { email }
            passwordStateFlow.update { password }
        }
    }


}