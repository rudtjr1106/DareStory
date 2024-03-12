package com.darestory.presentation.ui.main.discussion.write

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.base.BaseViewModel
import com.darestory.presentation.util.SelectedBook
import com.darestory.presentation.util.TimeFormatter
import com.darestory.presentation.util.UserInfo
import com.darestory.domain.model.enums.WriteType
import com.darestory.domain.model.vo.BookVo
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.model.vo.UploadDiscussionVo
import com.darestory.domain.usecase.discussion.GetDiscussionUseCase
import com.darestory.domain.usecase.discussion.UploadDiscussionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscussionWriteViewModel @Inject constructor(
    private val getDiscussionUseCase: GetDiscussionUseCase,
    private val uploadDiscussionUseCase: UploadDiscussionUseCase
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
    private lateinit var remainDiscussionVo : DiscussionVo

    fun loadPage(discussionId : Int, type : WriteType){
        this.type = type
        when(type){
            WriteType.EDIT -> getDiscussionDetail(discussionId)
            WriteType.NEW -> {}
        }
    }

    private fun getDiscussionDetail(discussionId: Int){
        viewModelScope.launch {
            showLoading()
            val result = getDiscussionUseCase(discussionId)
            endLoading()
            if(result.title.isNotEmpty()) successGetDiscussionDetail(result) else emitEventFlow(DiscussionWriteEvent.DeleteDiscussionEvent)
        }
    }

    private fun successGetDiscussionDetail(result : DiscussionVo){
        val bookInfo = BookVo(
            image = result.bookImage,
            title = result.bookTitle,
            author = result.bookAuthor
        )
        remainDiscussionVo = result
        viewModelScope.launch {
            SelectedBook.updateInfo(bookInfo)
            titleStateFlow.update { result.title }
            contentStateFlow.update { result.content }
        }
        emitEventFlow(DiscussionWriteEvent.SuccessGetDiscussionEvent)
    }

    fun onClickBackBtn(){
        emitEventFlow(DiscussionWriteEvent.OnClickBackEvent)
    }

    fun onClickUploadBtn(){
        val titleTrim = titleStateFlow.value.trim()
        val contentTrim = contentStateFlow.value.trim()
        if(titleTrim.isNotEmpty() && contentTrim.isNotEmpty() && SelectedBook.book.title.isNotEmpty()){
            val request = when(type){
                WriteType.EDIT -> getEditRequest()
                WriteType.NEW -> getNewRequest()
            }

            viewModelScope.launch {
                showLoading()
                val result = uploadDiscussionUseCase(request)
                endLoading()
                if(result) successUploadDiscussion() else emitEventFlow(DiscussionWriteEvent.ErrorUploadEvent)
            }
        }
        checkEmpty()
    }

    private fun checkEmpty(){
        if(titleStateFlow.value.isEmpty()){
            emitEventFlow(DiscussionWriteEvent.ToastEmptyTitleEvent)
        }
        else if(contentStateFlow.value.isEmpty()){
            emitEventFlow(DiscussionWriteEvent.ToastEmptyContentEvent)
        }
        else if(SelectedBook.book.title.isEmpty()){
            emitEventFlow(DiscussionWriteEvent.ToastEmptyBookEvent)
        }
    }

    fun onClickBookSearch(){
        emitEventFlow(DiscussionWriteEvent.GoToBookSearch)
    }

    private fun getNewRequest(): UploadDiscussionVo {
        val discussionVo = DiscussionVo(
            age = UserInfo.info.age,
            author = UserInfo.info.nickName,
            bookAuthor = SelectedBook.book.author,
            bookImage = SelectedBook.book.image,
            bookTitle = SelectedBook.book.title,
            content = contentStateFlow.value,
            createdAt = TimeFormatter.getNowDateAndTime(),
            title = titleStateFlow.value,
            token = UserInfo.info.token
        )
        return UploadDiscussionVo(
            type = type,
            discussionVo = discussionVo
        )
    }

    private fun getEditRequest() : UploadDiscussionVo {
        val discussionVo = remainDiscussionVo.copy(
            bookAuthor = SelectedBook.book.author,
            bookImage = SelectedBook.book.image,
            bookTitle = SelectedBook.book.title,
            title = titleStateFlow.value,
            content = contentStateFlow.value,
        )

        return UploadDiscussionVo(
            type = type,
            discussionVo = discussionVo
        )
    }

    private fun successUploadDiscussion(){
        emitEventFlow(DiscussionWriteEvent.SuccessUploadEvent)
    }
}