package com.example.darestory.ui.main.my

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.UserInfo
import com.example.domain.model.vo.UserVo
import com.example.domain.usecase.my.GetMyInfoUseCase
import com.example.domain.usecase.sign.GetAllNickNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
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
            if(result.nickName.isNotEmpty()) successGetMyInfo(result)
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
}