package com.darestory.presentation.ui.main.home.detail.wrtie

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.base.BaseViewModel
import com.darestory.presentation.util.TimeFormatter
import com.darestory.presentation.util.UserInfo
import com.darestory.domain.model.enums.WriteType
import com.darestory.domain.model.vo.UploadProseVo
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.usecase.home.GetProseUseCase
import com.darestory.domain.usecase.home.UploadProseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProseWriteViewModel @Inject constructor(
    private val getProseUseCase: GetProseUseCase,
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

    private lateinit var type : WriteType
    private lateinit var remainProseVo : ProseVo
    fun loadPage(proseId : Int, type : WriteType){
        this.type = type
        when(type){
            WriteType.EDIT -> getProseDetail(proseId)
            WriteType.NEW -> {}
        }
    }

    fun onClickAuthorSay(){
        emitEventFlow(ProseWriteEvent.FocusEditTextEvent)
    }

    private fun getProseDetail(proseId: Int){
        viewModelScope.launch {
            showLoading()
            val result = getProseUseCase(proseId)
            endLoading()
            if(result.title.isNotEmpty()) successGetProseDetail(result) else emitEventFlow(ProseWriteEvent.DeleteProseEvent)
        }
    }

    private fun successGetProseDetail(result : ProseVo){
        remainProseVo = result
        viewModelScope.launch {
            titleStateFlow.update { result.title }
            contentStateFlow.update { result.content }
            authorSayStateFlow.update { result.authorSay }
        }
    }

    fun onClickBackBtn(){
        emitEventFlow(ProseWriteEvent.OnClickBackEvent)
    }

    fun onClickUploadBtn(){
        val titleTrim = titleStateFlow.value.trim()
        val contentTrim = contentStateFlow.value.trim()
        if(titleTrim.isNotEmpty() && contentTrim.isNotEmpty()){
            val request = when(type){
                WriteType.EDIT -> getEditRequest()
                WriteType.NEW -> getNewRequest()
            }

            viewModelScope.launch {
                showLoading()
                val result = uploadProseUseCase(request)
                endLoading()
                if(result) successUploadProse() else emitEventFlow(ProseWriteEvent.ErrorUploadEvent)
            }
        }
        checkEmpty()
    }

    private fun checkEmpty(){
        if(titleStateFlow.value.isEmpty()){
            emitEventFlow(ProseWriteEvent.ToastEmptyTitleEvent)
        }
        else if(contentStateFlow.value.isEmpty()){
            emitEventFlow(ProseWriteEvent.ToastEmptyContentEvent)
        }
    }

    private fun getNewRequest(): UploadProseVo {
        val proseVo = ProseVo(
            age = UserInfo.info.age,
            author = UserInfo.info.nickName,
            authorSay = authorSayStateFlow.value,
            content = contentStateFlow.value,
            createdAt = TimeFormatter.getNowDateAndTime(),
            title = titleStateFlow.value,
            token = UserInfo.info.token,
        )
        return UploadProseVo(
            type = type,
            proseVo = proseVo
        )
    }

    private fun getEditRequest() : UploadProseVo {
        val proseVo = remainProseVo.copy(
            title = titleStateFlow.value,
            content = contentStateFlow.value,
            authorSay = authorSayStateFlow.value
        )

        return UploadProseVo(
            type = type,
            proseVo = proseVo
        )
    }

    private fun successUploadProse(){
        emitEventFlow(ProseWriteEvent.SuccessUploadEvent)
    }

}