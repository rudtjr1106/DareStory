package com.example.darestory.ui.main.discussion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.DiscussionViewType
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.MainDiscussionVo
import com.example.domain.usecase.discussion.GetAllDiscussionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscussionViewModel @Inject constructor(
    private val getAllDiscussionUseCase: GetAllDiscussionUseCase
) : BaseViewModel<DiscussionPageState>() {

    private val discussionListStateFlow: MutableStateFlow<List<MainDiscussionVo>> = MutableStateFlow(emptyList())
    private val sortTypeStateFlow: MutableStateFlow<SortType> = MutableStateFlow(SortType.POPULAR)

    override val uiState: DiscussionPageState = DiscussionPageState(
        discussionListStateFlow.asStateFlow(),
        sortTypeStateFlow.asStateFlow()
    )

    fun getAllDiscussionList(){
        viewModelScope.launch {
            showLoading()
            val result = getAllDiscussionUseCase(Unit)
            endLoading()
            successGetAllDiscussion(result)
        }
    }

    private fun successGetAllDiscussion(result : List<DiscussionVo>){
        endLoading()
        val sortedByTypeList = getSortedByType(result)
        val topViewList = getTopDiscussion()
        val discussionList = getDiscussionViewList(sortedByTypeList)
        updateDiscussionList(topViewList + discussionList)
    }

    private fun getSortedByType(list : List<DiscussionVo>): List<DiscussionVo>{
        return when(sortTypeStateFlow.value){
            SortType.POPULAR -> getSortedByPopularList(list)
            SortType.RECENT -> getSortedByRecentList(list)
            SortType.AGE -> getSortedByAgeList(list)
        }
    }

    private fun getSortedByPopularList(list : List<DiscussionVo>) : List<DiscussionVo>{
        return list.sortedByDescending { it.commentCount + it.likeCount }
    }

    private fun getSortedByRecentList(list : List<DiscussionVo>) : List<DiscussionVo>{
        return list.sortedByDescending { it.createdAt }
    }

    private fun getSortedByAgeList(list : List<DiscussionVo>) : List<DiscussionVo>{
        return list.filter { it.age == UserInfo.info.age }
    }

    private fun getTopDiscussion() : List<MainDiscussionVo>{
        return listOf(MainDiscussionVo(viewType = DiscussionViewType.TOP))
    }

    private fun getDiscussionViewList(list : List<DiscussionVo>) : List<MainDiscussionVo>{
        val mainDiscussionList = mutableListOf<MainDiscussionVo>()
        list.forEach {
            mainDiscussionList.add(
                MainDiscussionVo(discussionVo = it, viewType = DiscussionViewType.DISCUSSION)
            )
        }

        return mainDiscussionList
    }

    private fun updateDiscussionList(list : List<MainDiscussionVo>){
        viewModelScope.launch {
            discussionListStateFlow.update { list }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSortType(type: SortType){
        viewModelScope.launch {
            sortTypeStateFlow.update { type }
            showLoading()
            val result = getAllDiscussionUseCase(Unit)
            successGetAllDiscussion(result)
        }
    }

    fun onClickScrollUp(){
        emitEventFlow(DiscussionEvent.ScrollUpEvent)
    }

    fun onClickBottomNavHome(){
        emitEventFlow(DiscussionEvent.GoToHomeEvent)
    }

    fun onClickBottomNavMy(){
        emitEventFlow(DiscussionEvent.GoToMyEvent)
    }
}