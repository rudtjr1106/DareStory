package com.example.darestory.ui.main.home.detail

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.TimeFormatter
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.BottomSheetMenuItemType
import com.example.domain.model.enums.BottomSheetType
import com.example.domain.model.enums.DetailPageViewType
import com.example.domain.model.enums.DetailType
import com.example.domain.model.vo.UpdateCommentVo
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.DetailContentVo
import com.example.domain.model.vo.DetailPageVo
import com.example.domain.model.vo.DisCommentVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.usecase.discussion.AddDiscussionCommentUseCase
import com.example.domain.usecase.discussion.DeleteDiscussionCommentUseCase
import com.example.domain.usecase.discussion.DeleteDiscussionUseCase
import com.example.domain.usecase.discussion.GetDiscussionUseCase
import com.example.domain.usecase.discussion.LikeDiscussionUseCase
import com.example.domain.usecase.home.AddProseCommentUseCase
import com.example.domain.usecase.home.DeleteProseCommentUseCase
import com.example.domain.usecase.home.DeleteProseUseCase
import com.example.domain.usecase.home.GetProseUseCase
import com.example.domain.usecase.home.LikeProseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProseUseCase: GetProseUseCase,
    private val getDiscussionUseCase: GetDiscussionUseCase,
    private val likeProseUseCase: LikeProseUseCase,
    private val likeDiscussionUseCase: LikeDiscussionUseCase,
    private val addProseCommentUseCase: AddProseCommentUseCase,
    private val addDiscussionCommentUseCase: AddDiscussionCommentUseCase,
    private val deleteProseUseCase: DeleteProseUseCase,
    private val deleteDiscussionUseCase: DeleteDiscussionUseCase,
    private val deleteProseCommentUseCase: DeleteProseCommentUseCase,
    private val deleteDiscussionCommentUseCase: DeleteDiscussionCommentUseCase,
) : BaseViewModel<DetailPageState>() {

    private val detailPageListStateFlow: MutableStateFlow<List<DetailPageVo>> = MutableStateFlow(emptyList())
    private var commentEditStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: DetailPageState = DetailPageState(
        detailPageListStateFlow.asStateFlow(),
        commentEditStateFlow
    )

    private var detailId by Delegates.notNull<Int>()
    private var detailType by Delegates.notNull<DetailType>()
    private var commentId by Delegates.notNull<Int>()
    private var reportWho by Delegates.notNull<String>()

    fun getDetail(id : Int, type : DetailType){
        detailId = id
        detailType = type
        when(type){
            DetailType.PROSE -> getProseDetail(id)
            DetailType.DISCUSSION -> getDiscussionDetail(id)
            DetailType.BOOK -> {}
        }
    }

    private fun getProseDetail(proseId : Int){
        viewModelScope.launch{
            val result = getProseUseCase(proseId)
            if(result.title.isNotEmpty()) successGetProseDetail(result)
        }
    }

    private fun getDiscussionDetail(discussionId : Int){
        viewModelScope.launch{
            val result = getDiscussionUseCase(discussionId)
            if(result.title.isNotEmpty()) successGetDiscussionDetail(result)
        }
    }

    private fun successGetProseDetail(result : ProseVo){
        val contentList = getProseContentList(result)
        val authorCommentList = if(result.authorSay.isNotEmpty()) getProseAuthorCommentList(result) else emptyList()
        val commentList = getProseCommentList(result.comment)
        updateDetailPageList(contentList + authorCommentList + commentList)
    }

    private fun successGetDiscussionDetail(result : DiscussionVo){
        val contentList = getDiscussionContentList(result)
        val commentList = getDiscussionCommentList(result.comment)
        updateDetailPageList(contentList + commentList)
    }

    private fun getProseContentList(vo : ProseVo) : List<DetailPageVo>{
        val detailContent = DetailContentVo(
            pageId = vo.proseId,
            pageType = DetailType.PROSE,
            author = vo.author,
            commentCount = vo.commentCount,
            content = vo.content,
            createdAt = vo.createdAt,
            likeCount = vo.likeCount,
            title = vo.title,
            isLiked = vo.whoLiked.containsValue(UserInfo.info.nickName)
        )

        return listOf(
            DetailPageVo(detailContent = detailContent, detailViewType = DetailPageViewType.CONTENT)
        )
    }

    private fun getDiscussionContentList(vo : DiscussionVo) : List<DetailPageVo>{
        val detailContent = DetailContentVo(
            pageId = vo.discussionId,
            pageType = DetailType.DISCUSSION,
            author = vo.author,
            commentCount = vo.commentCount,
            content = vo.content,
            createdAt = vo.createdAt,
            likeCount = vo.likeCount,
            title = vo.title,
            isLiked = vo.whoLiked.containsValue(UserInfo.info.nickName)
        )

        return listOf(
            DetailPageVo(detailContent = detailContent, detailViewType = DetailPageViewType.CONTENT)
        )
    }

    private fun getProseAuthorCommentList(vo : ProseVo) : List<DetailPageVo>{
        val authorComment = CommentVo(
            commentId = -1,
            content = vo.authorSay,
            writer = vo.author
        )

        return listOf(
            DetailPageVo(proseComment = authorComment, detailViewType = DetailPageViewType.PROSE_AUTHOR_COMMENT)
        )
    }

    private fun getProseCommentList(map : HashMap<String, CommentVo>) : List<DetailPageVo>{
        val list: MutableList<CommentVo> = mutableListOf()
        map.forEach { list.add(it.value) }
        val proseCommentList = mutableListOf<DetailPageVo>()
        list.forEach {
            proseCommentList.add(
                DetailPageVo(proseComment = it, detailViewType = DetailPageViewType.PROSE_COMMENT)
            )
        }
        if(proseCommentList.isEmpty()) proseCommentList.add(DetailPageVo(detailViewType = DetailPageViewType.EMPTY))
        return proseCommentList
    }

    private fun getDiscussionCommentList(map : HashMap<String, DisCommentVo>) : List<DetailPageVo>{
        val list: MutableList<DisCommentVo> = mutableListOf()
        map.forEach { list.add(it.value) }
        val discussionCommentList = mutableListOf<DetailPageVo>()
        list.forEach {
            discussionCommentList.add(
                DetailPageVo(discussionComment = it, detailViewType = DetailPageViewType.DISCUSSION_COMMENT)
            )
        }
        if(discussionCommentList.isEmpty()) discussionCommentList.add(DetailPageVo(detailViewType = DetailPageViewType.EMPTY))
        return discussionCommentList
    }

    private fun updateDetailPageList(list : List<DetailPageVo>){
        viewModelScope.launch {
            detailPageListStateFlow.update { list }
        }
    }

    fun onClickLikeBtn(id : Int, type : DetailType, isLiked : Boolean){
        val request = LikeVo(
            pageId = id,
            nickName = UserInfo.info.nickName,
            isLiked = isLiked
        )
        when(type){
            DetailType.PROSE -> proseLikeBtn(request)
            DetailType.DISCUSSION -> discussionLikeBtn(request)
            DetailType.BOOK -> {}
        }
    }

    private fun proseLikeBtn(request : LikeVo){
        viewModelScope.launch {
            val result = likeProseUseCase(request)
            if(result) reloadPage()
        }
    }

    private fun discussionLikeBtn(request : LikeVo){
        viewModelScope.launch {
            val result = likeDiscussionUseCase(request)
            if(result) reloadPage()
        }
    }

    fun onClickBackButton(){
        emitEventFlow(DetailEvent.GoToBack)
    }

    fun onClickCommentAddButton(){
        if(commentEditStateFlow.value.isNotEmpty()){
            val request = UpdateCommentVo(
                id = detailId,
                comment = CommentVo(
                    content = commentEditStateFlow.value,
                    date = TimeFormatter.getNowDateAndTime(),
                    writer = UserInfo.info.nickName
                )

            )
            viewModelScope.launch {
                showLoading()
                val result = when(detailType){
                    DetailType.PROSE -> addProseCommentUseCase(request)
                    DetailType.DISCUSSION -> addDiscussionCommentUseCase(request)
                    DetailType.BOOK -> TODO()
                }
                if(result) successAddComment()
            }
        }
    }

    private fun successAddComment(){
        endLoading()
        viewModelScope.launch {
            commentEditStateFlow.update { "" }
        }
        reloadPage()
    }

    private fun reloadPage(){
        getDetail(detailId, detailType)
    }

    fun onClickContentMenu(type : DetailType, author : String){
        reportWho = author
        val contentData = detailPageListStateFlow.value.find { it.detailViewType == DetailPageViewType.CONTENT }
        when(type){
            DetailType.PROSE -> contentData?.let { showProseBottomSheet(it) }
            DetailType.DISCUSSION -> contentData?.let { showDiscussionBottomSheet(it) }
            DetailType.BOOK -> { }
        }
    }

    fun onClickCommentMenu(commentId : Int, type: DetailType, writer : String){
        reportWho = writer
        this.commentId = commentId
        when(type){
            DetailType.PROSE -> {
                val contentData = detailPageListStateFlow.value.find { it.proseComment.commentId == commentId }
                contentData?.let { showCommentBottomSheet(it) }
            }
            DetailType.DISCUSSION -> {
                val contentData = detailPageListStateFlow.value.find { it.discussionComment.commentId == commentId }
                contentData?.let { showCommentBottomSheet(it) }
            }
            DetailType.BOOK -> {}
        }
    }

    private fun showProseBottomSheet(content : DetailPageVo){
        val event = if(content.detailContent.author == UserInfo.info.nickName) DetailEvent.ShowBottomSheetEvent(BottomSheetType.PROSE_AUTHOR)
        else DetailEvent.ShowBottomSheetEvent(BottomSheetType.PROSE_NORMAL)
        emitEventFlow(event)
    }

    private fun showDiscussionBottomSheet(content : DetailPageVo){
        val event = if(content.detailContent.author == UserInfo.info.nickName) DetailEvent.ShowBottomSheetEvent(BottomSheetType.DISCUSSION_AUTHOR)
        else DetailEvent.ShowBottomSheetEvent(BottomSheetType.DISCUSSION_NORMAL)
        emitEventFlow(event)
    }

    private fun showCommentBottomSheet(content : DetailPageVo){
        val event = if(content.proseComment.writer == UserInfo.info.nickName
            || content.discussionComment.writer == UserInfo.info.nickName) DetailEvent.ShowBottomSheetEvent(BottomSheetType.COMMENT_WRITER)
        else DetailEvent.ShowBottomSheetEvent(BottomSheetType.COMMENT_NORMAL)
        emitEventFlow(event)
    }

    fun onClickImageMenuItemType(item : BottomSheetMenuItemType){
       when(item){
           BottomSheetMenuItemType.DISCUSSION_EDIT,
           BottomSheetMenuItemType.PROSE_EDIT -> emitEventFlow(DetailEvent.GoEditEvent)

           BottomSheetMenuItemType.DISCUSSION_DELETE,
           BottomSheetMenuItemType.PROSE_DELETE -> emitEventFlow(DetailEvent.ShowDeleteDialogEvent)

           BottomSheetMenuItemType.REPORT -> emitEventFlow(DetailEvent.GoReportEvent(reportWho))
           BottomSheetMenuItemType.PROSE_BOOKMARK -> {}
           BottomSheetMenuItemType.COMMENT_DELETE -> emitEventFlow(DetailEvent.ShowCommentDeleteDialogEvent)
       }
    }

    fun deleteThis(id : Int){
        viewModelScope.launch {
            showLoading()
            val result = when(detailType){
                DetailType.PROSE -> deleteProseUseCase(id)
                DetailType.DISCUSSION -> deleteDiscussionUseCase(id)
                DetailType.BOOK -> false
            }
            endLoading()
            if(result) onClickBackButton()
        }
    }

    fun deleteComment(contentId : Int){
        val request = UpdateCommentVo(
            id = contentId,
            comment = CommentVo(commentId = commentId)
        )
        when(detailType){
            DetailType.PROSE -> deleteProseComment(request)
            DetailType.DISCUSSION -> deleteDiscussionComment(request)
            DetailType.BOOK -> {}
        }
    }

    private fun deleteProseComment(request : UpdateCommentVo){
        viewModelScope.launch {
            val result = deleteProseCommentUseCase(request)
            if(result) reloadPage()
        }
    }

    private fun deleteDiscussionComment(request : UpdateCommentVo){
        viewModelScope.launch {
            val result = deleteDiscussionCommentUseCase(request)
            if(result) reloadPage()
        }
    }
}