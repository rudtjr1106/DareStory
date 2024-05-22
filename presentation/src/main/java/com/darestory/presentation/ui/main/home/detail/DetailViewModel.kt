package com.darestory.presentation.ui.main.home.detail

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.FcmNotification
import com.darestory.presentation.base.BaseViewModel
import com.darestory.presentation.util.SelectedMyOwnBook
import com.darestory.presentation.util.TimeFormatter
import com.darestory.presentation.util.UserInfo
import com.darestory.domain.model.enums.BottomSheetMenuItemType
import com.darestory.domain.model.enums.BottomSheetType
import com.darestory.domain.model.enums.DetailPageViewType
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.vo.MyOwnBookProseRequestVo
import com.darestory.domain.model.vo.UpdateCommentVo
import com.darestory.domain.model.vo.CommentVo
import com.darestory.domain.model.vo.DetailContentVo
import com.darestory.domain.model.vo.DetailPageVo
import com.darestory.domain.model.vo.DisCommentVo
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.model.vo.LikeVo
import com.darestory.domain.model.vo.MyBookVo
import com.darestory.domain.model.vo.NotificationVo
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.usecase.discussion.AddDiscussionCommentUseCase
import com.darestory.domain.usecase.discussion.DeleteDiscussionCommentUseCase
import com.darestory.domain.usecase.discussion.DeleteDiscussionUseCase
import com.darestory.domain.usecase.discussion.GetDiscussionUseCase
import com.darestory.domain.usecase.discussion.LikeDiscussionUseCase
import com.darestory.domain.usecase.home.AddProseCommentUseCase
import com.darestory.domain.usecase.home.DeleteProseCommentUseCase
import com.darestory.domain.usecase.home.DeleteProseUseCase
import com.darestory.domain.usecase.home.GetProseUseCase
import com.darestory.domain.usecase.home.LikeProseUseCase
import com.darestory.domain.usecase.my.AddMyOwnBookProseUseCase
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
    private val addMyOwnBookProseUseCase : AddMyOwnBookProseUseCase
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
    private var proseVo by Delegates.notNull<ProseVo>()
    private var discussionVo by Delegates.notNull<DiscussionVo>()
    private val fcmNotification = FcmNotification()

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
            showLoading()
            val result = getProseUseCase(proseId)
            endLoading()
            if(result.title.isNotEmpty()) successGetProseDetail(result) else emitEventFlow(DetailEvent.DeleteProseErrorEvent)
        }
    }

    private fun getDiscussionDetail(discussionId : Int){
        viewModelScope.launch{
            showLoading()
            val result = getDiscussionUseCase(discussionId)
            endLoading()
            if(result.title.isNotEmpty()) successGetDiscussionDetail(result)else emitEventFlow(DetailEvent.DeleteProseErrorEvent)
        }
    }

    private fun successGetProseDetail(result : ProseVo){
        proseVo = result
        val contentList = getProseContentList(result)
        val authorCommentList = if(result.authorSay.isNotEmpty()) getProseAuthorCommentList(result) else emptyList()
        val commentList = getProseCommentList(result.comment)
        updateDetailPageList(contentList + authorCommentList + commentList)
    }

    private fun successGetDiscussionDetail(result : DiscussionVo){
        discussionVo = result
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
        val sortedList = list.sortedByDescending { it.date }.reversed()
        val proseCommentList = mutableListOf<DetailPageVo>()
        sortedList.forEach {
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
        val sortedList = list.sortedByDescending { it.date }.reversed()
        val discussionCommentList = mutableListOf<DetailPageVo>()
        sortedList.forEach {
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
            showLoading()
            val result = likeProseUseCase(request)
            endLoading()
            if(result) {
                sendLikeFcmMessage(request.isLiked)
                reloadPage()
            }
            else{
                emitEventFlow(DetailEvent.DeleteProseErrorEvent)
            }
        }
    }

    private fun discussionLikeBtn(request : LikeVo){
        viewModelScope.launch {
            showLoading()
            val result = likeDiscussionUseCase(request)
            endLoading()
            if(result) {
                sendLikeFcmMessage(request.isLiked)
                reloadPage()
            }
            else{
                emitEventFlow(DetailEvent.DeleteDiscussionErrorEvent)
            }
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
                    token = UserInfo.info.token,
                    writer = UserInfo.info.nickName
                )

            )
            viewModelScope.launch {
                showLoading()
                val result = when(detailType){
                    DetailType.PROSE -> addProseCommentUseCase(request)
                    DetailType.DISCUSSION -> addDiscussionCommentUseCase(request)
                    DetailType.BOOK -> false
                }
                endLoading()
                if(result) {
                    sendCommentFcmMessage()
                    successAddComment()
                }
                else{
                    when(detailType){
                        DetailType.PROSE -> emitEventFlow(DetailEvent.DeleteProseErrorEvent)
                        DetailType.DISCUSSION -> emitEventFlow(DetailEvent.DeleteDiscussionErrorEvent)
                        DetailType.BOOK -> {}
                    }
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
           BottomSheetMenuItemType.PROSE_BOOKMARK -> emitEventFlow(DetailEvent.GoToMyOwnBookSelectEvent)
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
            showLoading()
            val result = deleteProseCommentUseCase(request)
            endLoading()
            if(result) reloadPage() else emitEventFlow(DetailEvent.DeleteProseErrorEvent)
        }
    }

    private fun deleteDiscussionComment(request : UpdateCommentVo){
        viewModelScope.launch {
            showLoading()
            val result = deleteDiscussionCommentUseCase(request)
            endLoading()
            if(result) reloadPage() else emitEventFlow(DetailEvent.DeleteDiscussionErrorEvent)
        }
    }

    fun updateMyOwnBookProse(){
        if(SelectedMyOwnBook.ownBook.myBookTitle.isNotEmpty()){
            addMyOwnBookProse()
        }
    }
    private fun addMyOwnBookProse(){
        val request = MyOwnBookProseRequestVo(
            title = SelectedMyOwnBook.ownBook.myBookTitle,
            proseVo = proseVo
        )
        viewModelScope.launch {
            val result = addMyOwnBookProseUseCase(request)
            if(result) SelectedMyOwnBook.updateInfo(MyBookVo())
        }
    }

    private fun sendLikeFcmMessage(isLiked: Boolean){
        val token = getFcmToken()
        val title = getDetailTitle()
        if(isLiked || token == UserInfo.info.token){
            return
        }
        else{
            fcmNotification.sendMessage(NotificationVo(token, NotificationVo.Data(
                body = "작가 ${UserInfo.info.nickName} 님이 작가님의 글에 좋은 마음을 남겼습니다!", title
            )))
        }
    }

    private fun getFcmToken() : String{
        return when(detailType){
            DetailType.PROSE -> proseVo.token
            DetailType.DISCUSSION -> discussionVo.token
            DetailType.BOOK -> ""
        }
    }

    private fun getDetailTitle() : String{
        return when(detailType){
            DetailType.PROSE -> proseVo.title
            DetailType.DISCUSSION -> discussionVo.title
            DetailType.BOOK -> ""
        }
    }

    private fun sendCommentFcmMessage(){
        val token = getFcmToken()
        val title = getDetailTitle()
        if(token == UserInfo.info.token){
            return
        }
        else{
            fcmNotification.sendMessage(NotificationVo(token, NotificationVo.Data(
                body = "작가 ${UserInfo.info.nickName} 님이 작가님의 글에 좋은 글귀를 남겼습니다!", title
            )))
        }
    }
}