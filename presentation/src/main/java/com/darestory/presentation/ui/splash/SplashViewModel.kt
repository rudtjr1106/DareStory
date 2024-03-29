package com.darestory.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.PageState
import com.darestory.presentation.base.BaseViewModel
import com.darestory.presentation.util.UserInfo
import com.darestory.domain.model.vo.AppInfoResponseVo
import com.darestory.domain.model.vo.UserVo
import com.darestory.domain.usecase.sign.CheckAppInfoUseCase
import com.darestory.domain.usecase.sign.CheckAutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAutoLoginUseCase: CheckAutoLoginUseCase,
    private val checkAppInfoUseCase: CheckAppInfoUseCase,
) : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun checkAppInfo(){
        viewModelScope.launch {
            val result = checkAppInfoUseCase(Unit)
            if(result.version.isNotEmpty()) checkAppServerOrVersion(result)
        }
    }

    private fun checkAppServerOrVersion(result : AppInfoResponseVo){
        if(!result.server){
            emitEventFlow(SplashEvent.ErrorServerEvent(result.serverTime))
        }
        else if(result.version != UserInfo.APP_VERSION){
            emitEventFlow(SplashEvent.ErrorVersionEvent)
        }
        else{
            checkLogin()
        }
    }

    private fun checkLogin(){
        viewModelScope.launch{
            val result = checkAutoLoginUseCase(Unit)
            if(result.userUid.isNotEmpty()) goToMain(result) else goToLogin()
        }
    }

    private fun goToMain(user : UserVo){
        UserInfo.updateInfo(user)
        emitEventFlow(SplashEvent.GoToMainEvent)
    }

    private fun goToLogin(){
        emitEventFlow(SplashEvent.GoToLoginEvent)
    }
}