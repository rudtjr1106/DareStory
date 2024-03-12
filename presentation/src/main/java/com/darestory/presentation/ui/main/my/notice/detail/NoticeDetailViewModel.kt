package com.darestory.presentation.ui.main.my.notice.detail

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.base.BaseViewModel
import com.darestory.domain.model.vo.NoticeVo
import com.darestory.domain.usecase.my.GetNoticeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
    private val getNoticeDetailUseCase: GetNoticeDetailUseCase
) : BaseViewModel<NoticeDetailPageState>() {

    private val titleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val createdAtStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: NoticeDetailPageState = NoticeDetailPageState(
        titleStateFlow,
        createdAtStateFlow,
        contentStateFlow,
    )

    fun getNoticeDetail(noticeId : Int){
        viewModelScope.launch {
            showLoading()
            val result = getNoticeDetailUseCase(noticeId)
            endLoading()
            if(result.title.isNotEmpty()) successGetNoticeDetail(result)
        }
    }

    private fun successGetNoticeDetail(result : NoticeVo){
        viewModelScope.launch {
            titleStateFlow.update { result.title.replace("\\n", "\n") }
            createdAtStateFlow.update { result.createdAt}
            contentStateFlow.update { result.content.replace("\\n", "\n") }
            emitEventFlow(NoticeDetailEvent.ShowWriterEvent)
        }
    }

    fun onClickBack(){
        emitEventFlow(NoticeDetailEvent.GoToBackEvent)
    }
}