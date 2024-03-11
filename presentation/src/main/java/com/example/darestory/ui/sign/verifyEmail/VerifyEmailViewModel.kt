package com.example.darestory.ui.sign.verifyEmail

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.UserInfo
import com.example.domain.model.vo.UserVo
import com.example.domain.usecase.my.GetMyInfoUseCase
import com.example.domain.usecase.sign.CheckEmailVerifiedUseCase
import com.example.domain.usecase.sign.SendEmailVerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val checkEmailVerifiedUseCase: CheckEmailVerifiedUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
) : BaseViewModel<PageState.Default>() {

    fun sendEmailVerification(){
        viewModelScope.launch {
            val result = sendEmailVerificationUseCase(Unit)
            if(!result) emitEventFlow(VerifyEmailEvent.ErrorSendEmailEvent)
        }
    }

    fun onClickEmail(email : String){
        val emailDomain = email.split("@").last()
        val url = "https://www.$emailDomain"

        emitEventFlow(VerifyEmailEvent.GoToUrl(url))
    }

    fun onClickResendText(){
        sendEmailVerification()
    }

    fun onClickVerifyComplete(){
        viewModelScope.launch {
            if(checkEmailVerifiedUseCase(Unit)) updateMyInfo() else showErrorToast()
        }
    }

    private fun updateMyInfo(){
        viewModelScope.launch {
            val result = getMyInfoUseCase(UserInfo.info.nickName)
            if(result.userUid.isNotEmpty()) goToMain(result)
        }
    }

    private fun goToMain(result : UserVo){
        UserInfo.updateInfo(result)
        emitEventFlow(VerifyEmailEvent.GoToMain)
    }

    private fun showErrorToast(){
        emitEventFlow(VerifyEmailEvent.ErrorVerify)
    }


    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")
}