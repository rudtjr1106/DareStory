package com.example.darestory.ui.sign.certifyEmail

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.domain.usecase.CheckEmailVerifiedUseCase
import com.example.domain.usecase.SendEmailVerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CertifyEmailViewModel @Inject constructor(
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

        emitEventFlow(CertifyEmailEvent.GoToUrl(url))
    }

    fun onClickResendText(){
        sendEmailVerification()
    }

    fun onClickCertifyComplete(){
        viewModelScope.launch {
            if(checkEmailVerifiedUseCase(Unit)) goToMain() else showErrorToast()
        }
    }

    private fun goToMain(){
        emitEventFlow(CertifyEmailEvent.GoToMain)
    }

    private fun showErrorToast(){
        emitEventFlow(CertifyEmailEvent.ErrorCertify)
    }


    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")
}