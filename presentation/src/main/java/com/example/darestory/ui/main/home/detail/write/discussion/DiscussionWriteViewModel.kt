package com.example.darestory.ui.main.home.detail.write.discussion

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.SelectedBook
import com.example.darestory.util.TimeFormatter
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.WriteType
import com.example.domain.model.vo.BookVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.UploadDiscussionVo
import com.example.domain.usecase.discussion.GetDiscussionUseCase
import com.example.domain.usecase.discussion.UploadDiscussionUseCase
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
            val result = getDiscussionUseCase(discussionId)
            if(result.discussionId != -1) successGetDiscussionDetail(result)
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
    }

    fun onClickBackBtn(){
        emitEventFlow(DiscussionWriteEvent.OnClickBackEvent)
    }

    fun onClickUploadBtn(){
        if(titleStateFlow.value.isNotEmpty() && contentStateFlow.value.isNotEmpty()){
            val request = when(type){
                WriteType.EDIT -> getEditRequest()
                WriteType.NEW -> getNewRequest()
            }

            viewModelScope.launch {
                showLoading()
                val result = uploadDiscussionUseCase(request)
                if(result) successUploadDiscussion()
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
        endLoading()
        emitEventFlow(DiscussionWriteEvent.SuccessUploadEvent)
    }
}