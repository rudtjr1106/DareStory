package com.example.darestory.ui.main.home.detail.write.discussion

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.ui.main.home.detail.write.prose.ProseWriteEvent
import com.example.domain.model.enums.WriteType
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.usecase.book.GetSearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscussionWriteViewModel @Inject constructor(
) : BaseViewModel<DiscussionWritePageState>() {

    private val titleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isBookSelectedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: DiscussionWritePageState = DiscussionWritePageState(
        titleStateFlow,
        contentStateFlow,
        isBookSelectedStateFlow.asStateFlow()
    )

    private lateinit var type : WriteType
    private lateinit var remainProseVo : DiscussionVo

    fun loadPage(discussionId : Int, type : WriteType){
        this.type = type
        when(type){
            WriteType.EDIT -> getDiscussionDetail(discussionId)
            WriteType.NEW -> {}
        }
    }

    private fun getDiscussionDetail(discussionId: Int){
        viewModelScope.launch {
//            val result = getProseUseCase(discussionId)
//            if(result.proseId != -1) successGetDiscussionDetail(result)
        }
    }

    private fun successGetDiscussionDetail(result : DiscussionVo){
        remainProseVo = result
        viewModelScope.launch {
            titleStateFlow.update { result.title }
            contentStateFlow.update { result.content }
        }
    }

    fun onClickBackBtn(){
        emitEventFlow(DiscussionWriteEvent.OnClickBackEvent)
    }

    fun onClickUploadBtn(){
        if(titleStateFlow.value.isNotEmpty() && contentStateFlow.value.isNotEmpty()){
//            val request = when(type){
//                WriteType.EDIT -> getEditRequest()
//                WriteType.NEW -> getNewRequest()
//            }

            viewModelScope.launch {
                showLoading()
//                val result = uploadProseUseCase(request)
//                if(result) successUploadProse()
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

    fun onClickBookSearch(){
        emitEventFlow(DiscussionWriteEvent.GoToBookSearch)
    }

//    private fun getNewRequest(): UploadProseVo {
//        val proseVo = ProseVo(
//            age = UserInfo.info.age,
//            author = UserInfo.info.nickName,
//            authorSay = authorSayStateFlow.value,
//            content = contentStateFlow.value,
//            createdAt = TimeFormatter.getNowDateAndTime(),
//            title = titleStateFlow.value,
//        )
//        return UploadProseVo(
//            type = type,
//            proseVo = proseVo
//        )
//    }
//
//    private fun getEditRequest() : UploadProseVo {
//        val proseVo = remainProseVo.copy(
//            title = titleStateFlow.value,
//            content = contentStateFlow.value,
//            authorSay = authorSayStateFlow.value
//        )
//
//        return UploadProseVo(
//            type = type,
//            proseVo = proseVo
//        )
//    }

    private fun successUploadProse(){
        endLoading()
        emitEventFlow(DiscussionWriteEvent.SuccessUploadEvent)
    }
}