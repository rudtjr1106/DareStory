package com.example.darestory.ui.main.home.detail.write.prose

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.TimeFormatter
import com.example.darestory.util.UserInfo
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.usecase.home.UploadProseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProseWriteViewModel @Inject constructor(
    private val uploadProseUseCase: UploadProseUseCase,
) : BaseViewModel<ProseWritePageState>() {

    private val titleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val authorSayStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: ProseWritePageState = ProseWritePageState(
        titleStateFlow,
        contentStateFlow,
        authorSayStateFlow
    )

    fun onClickBackBtn(){
        emitEventFlow(ProseWriteEvent.OnClickBack)
    }

    fun onClickUploadBtn(){
        val request = ProseVo(
            age = UserInfo.info.age,
            author = UserInfo.info.nickName,
            authorSay = authorSayStateFlow.value,
            content = contentStateFlow.value,
            createdAt = TimeFormatter.getNowDateAndTime(),
            title = titleStateFlow.value,
        )

        viewModelScope.launch {
            showLoading()
            val result = uploadProseUseCase(request)
            if(result) successUploadProse()
        }
    }

    private fun successUploadProse(){
        endLoading()
        emitEventFlow(ProseWriteEvent.SuccessUpload)
    }

}