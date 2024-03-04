package com.example.darestory.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.UserInfo
import com.example.domain.model.vo.UserVo
import com.example.domain.usecase.sign.CheckAutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAutoLoginUseCase: CheckAutoLoginUseCase
) : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun checkLogin(){
        viewModelScope.launch{
            val result = checkAutoLoginUseCase(Unit)
            if(result.userUid.isNotEmpty()) goToMain(result) else goToLogin()
        }
    }

    private fun goToMain(result : UserVo){
        UserInfo.updateInfo(result)
        emitEventFlow(SplashEvent.GoToMainEvent)
    }

    private fun goToLogin(){
        emitEventFlow(SplashEvent.GoToLoginEvent)
    }
}