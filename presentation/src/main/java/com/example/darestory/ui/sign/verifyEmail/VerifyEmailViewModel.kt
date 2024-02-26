package com.example.darestory.ui.sign.verifyEmail

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.domain.usecase.CheckEmailVerifiedUseCase
import com.example.domain.usecase.SendEmailVerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val checkEmailVerifiedUseCase: CheckEmailVerifiedUseCase
) : BaseViewModel<PageState.Default>() {

    fun sendEmailVerification(){
        viewModelScope.launch {
            sendEmailVerificationUseCase(Unit)
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
            if(checkEmailVerifiedUseCase(Unit)) goToMain() else showErrorToast()
        }
    }

    private fun goToMain(){
        emitEventFlow(VerifyEmailEvent.GoToMain)
    }

    private fun showErrorToast(){
        emitEventFlow(VerifyEmailEvent.ErrorVerify)
    }


    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")
}