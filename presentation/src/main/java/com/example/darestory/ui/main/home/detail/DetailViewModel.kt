package com.example.darestory.ui.main.home.detail

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.DareLog
import com.example.darestory.util.TimeFormatter
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.BottomSheetMenuItemType
import com.example.domain.model.enums.BottomSheetType
import com.example.domain.model.enums.DetailPageViewType
import com.example.domain.model.enums.DetailType
import com.example.domain.model.vo.AddCommentVo
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.DetailContentVo
import com.example.domain.model.vo.DetailPageVo
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.usecase.home.AddProseCommentUseCase
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
    private val proseLikeProseUseCase: LikeProseUseCase,
    private val addProseCommentUseCase: AddProseCommentUseCase
) : BaseViewModel<DetailPageState>() {

    private val detailPageListStateFlow: MutableStateFlow<List<DetailPageVo>> = MutableStateFlow(emptyList())
    private var commentEditStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: DetailPageState = DetailPageState(
        detailPageListStateFlow.asStateFlow(),
        commentEditStateFlow
    )

    private var detailid by Delegates.notNull<Int>()
    private var detailType by Delegates.notNull<DetailType>()

    fun getDetail(id : Int, type : DetailType){
        detailid = id
        detailType = type
        when(type){
            DetailType.PROSE -> getProseDetail(id)
            DetailType.DISCUSSION -> TODO()
        }
    }

    private fun getProseDetail(proseId : Int){
        viewModelScope.launch{
            val result = getProseUseCase(proseId)
            if(result.title.isNotEmpty()) successGetProseDetail(result)
        }
    }

    private fun successGetProseDetail(result : ProseVo){
        val contentList = getProseContentList(result)
        val authorCommentList = getProseAuthorCommentList(result)
        val commentList = getProseCommentList(result.comment)
        updateDetailPageList(contentList + authorCommentList + commentList)
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

    private fun getProseCommentList(list : List<CommentVo>) : List<DetailPageVo>{
        val proseCommentList = mutableListOf<DetailPageVo>()
        list.forEach {
            if(it != null){
                proseCommentList.add(
                    DetailPageVo(proseComment = it, detailViewType = DetailPageViewType.PROSE_COMMENT)
                )
            }
        }
        return proseCommentList

    }

    private fun updateDetailPageList(list : List<DetailPageVo>){
        viewModelScope.launch {
            detailPageListStateFlow.update { list }
        }
    }

    fun onClickLikeBtn(id : Int, type : DetailType, isLiked : Boolean){
        val request = LikeVo(
            id = id,
            nickName = UserInfo.info.nickName,
            isLiked = isLiked
        )
        when(type){
            DetailType.PROSE -> proseLikeBtn(request)
            DetailType.DISCUSSION -> {}
        }
    }

    private fun proseLikeBtn(request : LikeVo){
        viewModelScope.launch {
            val result = proseLikeProseUseCase(request)
            if(result) reloadPage()
        }
    }

    fun onClickBackButton(){
        emitEventFlow(DetailEvent.GoToBack)
    }

    fun onClickCommentAddButton(){
        val contentData = detailPageListStateFlow.value.find { it.detailViewType == DetailPageViewType.CONTENT }
        val request = AddCommentVo(
            id = contentData?.detailContent?.pageId ?: -1,
            comment = CommentVo(
                content = commentEditStateFlow.value,
                date = TimeFormatter.getNowDateAndTime(),
                writer = UserInfo.info.nickName
            )

        )
        viewModelScope.launch {
            val result = addProseCommentUseCase(request)
            if(result) successAddComment()
        }
    }

    private fun successAddComment(){
        viewModelScope.launch {
            commentEditStateFlow.update { "" }
        }
        reloadPage()
    }

    private fun reloadPage(){
        getDetail(detailid, detailType)
    }

    fun onClickContentMenu(type : DetailType){
        val contentData = detailPageListStateFlow.value.find { it.detailViewType == DetailPageViewType.CONTENT }
        val event = if(contentData?.detailContent?.author.equals(UserInfo.info.nickName)) DetailEvent.ShowBottomSheetEvent(BottomSheetType.PROSE_AUTHOR)
                    else DetailEvent.ShowBottomSheetEvent(BottomSheetType.PROSE_NORMAL)
        emitEventFlow(event)
    }

    fun onClickImageMenuItemType(item : BottomSheetMenuItemType){
        DareLog.D(item.name)
    }
}