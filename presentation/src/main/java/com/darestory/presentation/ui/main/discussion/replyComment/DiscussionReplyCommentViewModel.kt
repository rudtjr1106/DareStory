package com.darestory.presentation.ui.main.discussion.replyComment

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.FcmNotification
import com.darestory.presentation.base.BaseViewModel
import com.darestory.presentation.util.TimeFormatter
import com.darestory.presentation.util.UserInfo
import com.darestory.domain.model.enums.BottomSheetMenuItemType
import com.darestory.domain.model.enums.BottomSheetType
import com.darestory.domain.model.enums.CommentType
import com.darestory.domain.model.vo.CommentRequestVo
import com.darestory.domain.model.vo.CommentVo
import com.darestory.domain.model.vo.DisCommentVo
import com.darestory.domain.model.vo.DiscussionCommentPageVo
import com.darestory.domain.model.vo.NotificationVo
import com.darestory.domain.model.vo.UpdateCommentVo
import com.darestory.domain.model.vo.UpdateReplyCommentVo
import com.darestory.domain.usecase.discussion.AddDiscussionReplyCommentUseCase
import com.darestory.domain.usecase.discussion.DeleteDiscussionCommentUseCase
import com.darestory.domain.usecase.discussion.DeleteDiscussionReplyCommentUseCase
import com.darestory.domain.usecase.discussion.GetReplyCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class DiscussionReplyCommentViewModel @Inject constructor(
    private val getReplyCommentUseCase: GetReplyCommentUseCase,
    private val deleteDiscussionCommentUseCase: DeleteDiscussionCommentUseCase,
    private val deleteDiscussionReplyCommentUseCase: DeleteDiscussionReplyCommentUseCase,
    private val addDiscussionReplyCommentUseCase: AddDiscussionReplyCommentUseCase
) : BaseViewModel<DiscussionReplyCommentPageState>() {

    private val commentListStateFlow : MutableStateFlow<List<DiscussionCommentPageVo>> = MutableStateFlow(emptyList())
    private var commentEditStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    override val uiState: DiscussionReplyCommentPageState = DiscussionReplyCommentPageState(
        commentListStateFlow.asStateFlow(),
        commentEditStateFlow
    )

    private var commentId by Delegates.notNull<Int>()
    private var discussionId by Delegates.notNull<Int>()
    private var replyCommentId by Delegates.notNull<Int>()
    private var commentVo by Delegates.notNull<DisCommentVo>()
    private var reportWho by Delegates.notNull<String>()
    private var commentType by Delegates.notNull<CommentType>()
    private val fcmNotification = FcmNotification()

    fun loadPage(discussionId : Int, commentId : Int){
        this.discussionId = discussionId
        this.commentId = commentId
        val requestVo = CommentRequestVo(
            discussionId, commentId
        )
        viewModelScope.launch {
            showLoading()
            val result = getReplyCommentUseCase(requestVo)
            endLoading()
            if(result.writer.isNotEmpty()) successGetReplyComment(result) else emitEventFlow(DiscussionReplyCommentEvent.DeleteCommentErrorEvent)
        }
    }

    private fun successGetReplyComment(result : DisCommentVo){
        commentVo = result
        val mainComment = getMainComment(result)
        val replyComment = getReplyCommentList(result.replyComment)
        updateCommentList(mainComment + replyComment)
    }

    private fun getMainComment(result: DisCommentVo) : List<DiscussionCommentPageVo>{
        val vo = DiscussionCommentPageVo(
            commentVo = result,
            type = CommentType.NORMAL
        )
        return listOf(vo)
    }

    private fun getReplyCommentList(map : HashMap<String, CommentVo>) : List<DiscussionCommentPageVo>{
        val list: MutableList<CommentVo> = mutableListOf()
        map.forEach { list.add(it.value) }
        val sortedList = getSortedReplyCommentList(list)
        val mutableReplyList = mutableListOf<DiscussionCommentPageVo>()
        sortedList.forEach {
            val disCommentVo = DisCommentVo(
                commentId = it.commentId,
                content = it.content,
                date = it.date,
                writer = it.writer
            )
            val vo = DiscussionCommentPageVo(
                commentVo = disCommentVo,
                type = CommentType.REPLY
            )
            mutableReplyList.add(vo)
        }
        return mutableReplyList
    }

    private fun getSortedReplyCommentList(list : List<CommentVo>) : List<CommentVo>{
        return list.sortedByDescending { it.date }.reversed()
    }

    private fun updateCommentList(list : List<DiscussionCommentPageVo>){
        viewModelScope.launch {
            commentListStateFlow.update { list }
        }
    }

    fun onClickCommentMenu(commentId : Int, writer : String, type: CommentType){
        reportWho = writer
        this.replyCommentId = commentId
        commentType = type
        showCommentBottomSheet(writer == UserInfo.info.nickName)
    }

    private fun showCommentBottomSheet(isWriter : Boolean){
        val event = if(isWriter) DiscussionReplyCommentEvent.ShowBottomSheetEvent(BottomSheetType.COMMENT_WRITER)
        else DiscussionReplyCommentEvent.ShowBottomSheetEvent(BottomSheetType.COMMENT_NORMAL)
        emitEventFlow(event)
    }

    fun onClickImageMenuItemType(item : BottomSheetMenuItemType){
        when(item){
            BottomSheetMenuItemType.DISCUSSION_EDIT,
            BottomSheetMenuItemType.PROSE_EDIT -> {}

            BottomSheetMenuItemType.DISCUSSION_DELETE,
            BottomSheetMenuItemType.PROSE_DELETE -> {}

            BottomSheetMenuItemType.REPORT -> emitEventFlow(DiscussionReplyCommentEvent.GoReportEvent(reportWho))
            BottomSheetMenuItemType.PROSE_BOOKMARK -> {}
            BottomSheetMenuItemType.COMMENT_DELETE -> emitEventFlow(DiscussionReplyCommentEvent.ShowCommentDeleteDialogEvent)
        }
    }

    fun deleteComment(discussionId: Int, commentId : Int){
        when(commentType){
            CommentType.NORMAL -> deleteMainComment(discussionId, commentId)
            CommentType.REPLY -> deleteReplyComment(discussionId, this.replyCommentId, commentId)
        }
    }

    private fun deleteMainComment(discussionId: Int, commentId: Int){
        val request = UpdateCommentVo(
            id = discussionId,
            comment = CommentVo(commentId = commentId)
        )
        viewModelScope.launch {
            showLoading()
            val result = deleteDiscussionCommentUseCase(request)
            endLoading()
            if(result) reloadPage()
        }
    }

    private fun deleteReplyComment(discussionId: Int, replyCommentId : Int, commentId: Int){
        val request = UpdateReplyCommentVo(
            id = discussionId,
            commentId = commentId,
            comment = CommentVo(commentId = replyCommentId)
        )
        viewModelScope.launch {
            val result = deleteDiscussionReplyCommentUseCase(request)
            if(result) reloadPage() else emitEventFlow(DiscussionReplyCommentEvent.DeleteCommentErrorEvent)
        }
    }

    fun onClickBackBtn(){
        emitEventFlow(DiscussionReplyCommentEvent.GoToBack)
    }

    fun onClickCommentAddButton(){
        if(commentEditStateFlow.value.isNotEmpty()){
            val request = UpdateReplyCommentVo(
                id = discussionId,
                commentId = commentId,
                comment = CommentVo(
                    content = commentEditStateFlow.value,
                    date = TimeFormatter.getNowDateAndTime(),
                    token = UserInfo.info.token,
                    writer = UserInfo.info.nickName
                )

            )
            viewModelScope.launch {
                showLoading()
                val result = addDiscussionReplyCommentUseCase(request)
                endLoading()
                if(result) {
                    sendReplyCommentFcmMessage()
                    successAddComment()
                }
                else{
                    emitEventFlow(DiscussionReplyCommentEvent.DeleteCommentErrorEvent)
                }
            }
        }
    }

    private fun successAddComment(){
        viewModelScope.launch {
            commentEditStateFlow.update { "" }
        }
        reloadPage()
    }

    private fun sendReplyCommentFcmMessage(){
        if(commentVo.token == UserInfo.info.token){
            return
        }
        else{
            fcmNotification.sendMessage(NotificationVo(commentVo.token, NotificationVo.Data(
                body = "작가 ${UserInfo.info.nickName} 님이 작가님의 의견에 답변을 남겼습니다!", commentVo.content
            )))
        }
    }
    private fun reloadPage(){
        loadPage(discussionId, commentId)
    }

}