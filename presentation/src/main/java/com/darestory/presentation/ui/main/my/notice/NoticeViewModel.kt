package com.darestory.presentation.ui.main.my.notice

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.base.BaseViewModel
import com.darestory.domain.model.vo.NoticeVo
import com.darestory.domain.usecase.my.GetNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getNoticeListUseCase: GetNoticeListUseCase
) : BaseViewModel<NoticePageState>() {

    private val noticeStateFlow : MutableStateFlow<List<NoticeVo>> = MutableStateFlow(emptyList())

    override val uiState: NoticePageState = NoticePageState(
        noticeStateFlow.asStateFlow(),
    )

    fun getNotice(){
        viewModelScope.launch {
            showLoading()
            val result = getNoticeListUseCase(Unit)
            endLoading()
            if(result.isNotEmpty()) successGetNotice(result)
        }
    }

    private fun successGetNotice(result : List<NoticeVo>){
        viewModelScope.launch {
            noticeStateFlow.update { result }
        }
    }

    fun onClickBack(){
        emitEventFlow(NoticeEvent.GoToBackEvent)
    }
}