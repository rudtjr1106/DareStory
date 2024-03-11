package com.example.darestory.ui.main.my.readOrOwnBook.write

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.ui.main.home.detail.wrtie.ProseWriteEvent
import com.example.domain.model.vo.MyBookVo
import com.example.domain.usecase.my.AddMyOwnBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOwnBookWriteViewModel @Inject constructor(
    private val addMyOwnBookUseCase: AddMyOwnBookUseCase,
) : BaseViewModel<MyOwnBookWritePageState>() {

    private val titleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: MyOwnBookWritePageState = MyOwnBookWritePageState(
        titleStateFlow,
        contentStateFlow,
    )
    fun onClickBackBtn(){
        emitEventFlow(ProseWriteEvent.OnClickBackEvent)
    }

    fun onClickUploadBtn(){
        val titleTrim = titleStateFlow.value.trim()
        val contentTrim = contentStateFlow.value.trim()
        if(titleTrim.isNotEmpty() && contentTrim.isNotEmpty()){
            val request = MyBookVo(
                myBookDescription = contentStateFlow.value,
                myBookTitle = titleStateFlow.value
            )
            viewModelScope.launch {
                showLoading()
                val result = addMyOwnBookUseCase(request)
                if(result) successAddMyOwnBook() else emitEventFlow(MyOwnBookWriteEvent.ErrorUploadEvent)
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
    private fun successAddMyOwnBook(){
        endLoading()
        emitEventFlow(MyOwnBookWriteEvent.OnClickBackEvent)
    }

}