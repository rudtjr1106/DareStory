package com.example.darestory.ui.main.home.detail

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.enums.DetailPageViewType
import com.example.domain.model.enums.DetailType
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.DetailContentVo
import com.example.domain.model.vo.DetailPageVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.usecase.home.GetProseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProseUseCase: GetProseUseCase
) : BaseViewModel<DetailPageState>() {

    private val detailPageListStateFlow: MutableStateFlow<List<DetailPageVo>> = MutableStateFlow(emptyList())

    override val uiState: DetailPageState = DetailPageState(
        detailPageListStateFlow.asStateFlow()
    )

    fun getDetail(id : Int, type : DetailType){
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
        val commentList = getProseCommentList(result.comment)
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
            title = vo.title
        )

        return listOf(
            DetailPageVo(detailContent = detailContent, detailViewType = DetailPageViewType.CONTENT)
        )
    }

    private fun getProseCommentList(vo : HashMap<String, CommentVo>) : List<DetailPageVo>{
        val proseCommentList = mutableListOf<DetailPageVo>()
        vo.forEach { id, commentVo ->
            proseCommentList.add(
                DetailPageVo(proseComment = commentVo, detailViewType = DetailPageViewType.PROSE_COMMENT)
            )
        }
        return proseCommentList

    }

    private fun updateDetailPageList(list : List<DetailPageVo>){
        viewModelScope.launch {
            detailPageListStateFlow.update { list }
        }
    }
}