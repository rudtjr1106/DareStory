package com.example.darestory.ui.main.my

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.ReadOrOwnType
import com.example.domain.model.vo.UserVo
import com.example.domain.usecase.my.GetMyInfoUseCase
import com.example.domain.usecase.sign.LogoutUseCase
import com.example.domain.usecase.sign.UnRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val unRegisterUseCase: UnRegisterUseCase,
) : BaseViewModel<MyPageState>() {

    private val nicknameStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val myProseCountStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val myDiscussionCountStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: MyPageState = MyPageState(
        nicknameStateFlow,
        myProseCountStateFlow,
        myDiscussionCountStateFlow
    )

    fun getMyInfo(){
        viewModelScope.launch {
            showLoading()
            val result = getMyInfoUseCase(UserInfo.info.nickName)
            endLoading()
            if(result.nickName.isNotEmpty()) successGetMyInfo(result) else emitEventFlow(MyEvent.ErrorMyInfoEvent)
        }
    }

    private fun successGetMyInfo(result : UserVo){
        UserInfo.updateInfo(result)
        updateNickName(result.nickName)
        updateMyProseCount(result.myProse.size.toString())
        updateMyDiscussionCount(result.myDiscussion.size.toString())
    }

    private fun updateNickName(nickname : String){
        viewModelScope.launch {
            nicknameStateFlow.update { nickname }
        }
    }

    private fun updateMyProseCount(proseNum : String){
        viewModelScope.launch {
            myProseCountStateFlow.update { proseNum }
        }
    }

    private fun updateMyDiscussionCount(discussionNum : String){
        viewModelScope.launch {
            myDiscussionCountStateFlow.update { discussionNum }
        }
    }

    fun onClickMyProse(){
        emitEventFlow(MyEvent.GoToMyProseAndDiscussionEvent(DetailType.PROSE))
    }

    fun onClickMyDiscussion(){
        emitEventFlow(MyEvent.GoToMyProseAndDiscussionEvent(DetailType.DISCUSSION))
    }

    fun onClickMyOwnBook(){
        emitEventFlow(MyEvent.GoToMyReadOrOwnBookEvent(ReadOrOwnType.OWN_BOOK))
    }

    fun onClickMyReadBook(){
        emitEventFlow(MyEvent.GoToMyReadOrOwnBookEvent(ReadOrOwnType.READ_BOOK))
    }

    fun onClickLogout(){
        emitEventFlow(MyEvent.ShowLogoutDialogEvent)
    }

    fun onClickUnRegister(){
        emitEventFlow(MyEvent.ShowUnRegisterDialogEvent)
    }

    fun logout(){
        viewModelScope.launch {
            val result = logoutUseCase(Unit)
            if(result) emitEventFlow(MyEvent.GoToLoginEvent)
        }
    }

    fun unregister(){
        viewModelScope.launch {
            val result = unRegisterUseCase(Unit)
            if(result) emitEventFlow(MyEvent.GoToLoginEvent)
        }
    }

    fun onClickNotice(){
        emitEventFlow(MyEvent.GoToNoticeEvent)
    }

    fun onClickAsk(){
        emitEventFlow(MyEvent.GoToSendEmailEvent)
    }

    fun onClickHome(){
        emitEventFlow(MyEvent.GoToHomeEvent)
    }

    fun onClickDiscussion(){
        emitEventFlow(MyEvent.GoToDiscussionEvent)
    }

    fun onClickServicePolicy(){
        emitEventFlow(MyEvent.GoToServiceNotionEvent)
    }

    fun onClickPrivatePolicy(){
        emitEventFlow(MyEvent.GoToPrivateNotionEvent)
    }

    fun onClickAppVersion(){
        emitEventFlow(MyEvent.ShowAppVersionToastEvent)
    }
}