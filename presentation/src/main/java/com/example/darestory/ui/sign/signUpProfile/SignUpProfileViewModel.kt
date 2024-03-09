package com.example.darestory.ui.sign.signUpProfile

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.DareLog
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.GenderType
import com.example.domain.model.error.NickNameError
import com.example.domain.model.vo.LoginVo
import com.example.domain.model.vo.UserVo
import com.example.domain.usecase.sign.GetAllNickNameUseCase
import com.example.domain.usecase.sign.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpProfileViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val checkNickNameUseCase: GetAllNickNameUseCase
) : BaseViewModel<SignUpProfilePageState>() {

    companion object{
        const val MIN_NICKNAME = 2
        const val MAX_NICKNAME = 10
    }

    private lateinit var nickNameList : List<String>

    private val emailStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val passwordStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val nicknameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val nicknameErrorStateFlow: MutableStateFlow<NickNameError> = MutableStateFlow(NickNameError.LengthError)
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

    fun getAllNickName(){
        viewModelScope.launch {
            nickNameList = checkNickNameUseCase(Unit)
        }
    }

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
        else if(nickNameList.contains(nickname)) updateNickNameState(NickNameError.DuplicateError)
        else updateNickNameState(NickNameError.NoError)

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
        val canUse = (nicknameErrorStateFlow.value == NickNameError.NoError)  && !ageIsEmptyStateFlow.value && (genderStateFlow.value != GenderType.NOTHING)
        viewModelScope.launch {
            isCompleteButtonEnableStateFlow.update { canUse }
        }
    }

    fun onClickCompleteButton(){
        val signUpVo = UserVo(
            email = emailStateFlow.value,
            nickName = nicknameStateFlow.value,
            age = ageStateFlow.value,
            gender = genderStateFlow.value.type)
        viewModelScope.launch {
            if(signUpUseCase(LoginVo(signUpVo.email, passwordStateFlow.value))) updateMyInfo(signUpVo) else DareLog.D("이메일 등록 실패")
        }
    }

    private fun updateMyInfo(request : UserVo){
        viewModelScope.launch {
            if(signUpUseCase.addMyInfo(request)) onSuccessUpdateInfo() else DareLog.D("정보 업데이트 실패")
        }
    }

    private fun onSuccessUpdateInfo(){
        UserInfo.updateInfo(UserVo(nickName = nicknameStateFlow.value))
        emitEventFlow(SignUpProfileEvent.GoCertifyEmail(emailStateFlow.value))
    }

    fun updateInfo(email : String, password : String){
        viewModelScope.launch {
            emailStateFlow.update { email }
            passwordStateFlow.update { password }
        }
    }


}