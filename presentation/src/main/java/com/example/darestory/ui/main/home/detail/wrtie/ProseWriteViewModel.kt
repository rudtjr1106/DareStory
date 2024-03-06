package com.example.darestory.ui.main.home.detail.wrtie

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.TimeFormatter
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.WriteType
import com.example.domain.model.vo.UploadProseVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.usecase.home.GetProseUseCase
import com.example.domain.usecase.home.UploadProseUseCase
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

    private fun getProseDetail(proseId: Int){
        viewModelScope.launch {
            val result = getProseUseCase(proseId)
            if(result.proseId != -1) successGetProseDetail(result)
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
                if(result) successUploadProse()
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
        endLoading()
        emitEventFlow(ProseWriteEvent.SuccessUploadEvent)
    }

}