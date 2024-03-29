package com.darestory.presentation.ui.main.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.darestory.presentation.base.BaseViewModel
import com.darestory.presentation.util.TimeFormatter
import com.darestory.presentation.util.UserInfo
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.enums.HomeViewType
import com.darestory.domain.model.enums.SortType
import com.darestory.domain.model.vo.HomeProseVo
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.usecase.home.GetAllProseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProseUseCase: GetAllProseUseCase,
) : BaseViewModel<HomePageState>() {

    companion object{
        const val MAX_TODAY_PROSE = 5
        const val DEFAULT_AUTHOR = "개발자"
        const val DEFAULT_TITLE = "이 글은 터치가 되지 않습니다."
        const val DEFAULT_CONTENT = "이 앱을 통해 모든 작가님들이 안식을 찾으시면 좋겠습니다.\n찬 바람이 불어도 조금이라도 따듯하게 빛을 비춰주는 태양처럼 항상 자리를 지키겠습니다."
        const val DEVELOP_PROSE_ID = -100
        const val SPLIT_DATE = 0
    }

    private val proseListStateFlow: MutableStateFlow<List<HomeProseVo>> = MutableStateFlow(emptyList())
    private val sortTypeStateFlow: MutableStateFlow<SortType> = MutableStateFlow(SortType.POPULAR)

    override val uiState: HomePageState = HomePageState(
        proseListStateFlow.asStateFlow(),
        sortTypeStateFlow.asStateFlow()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllProse(){
        viewModelScope.launch {
            showLoading()
            val result = getAllProseUseCase(Unit)
            endLoading()
            if(result.isNotEmpty()) successGetAllProse(result) else showDefaultPage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSortType(type: SortType){
        viewModelScope.launch {
            sortTypeStateFlow.update { type }
            getAllProse()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun successGetAllProse(list : List<ProseVo>){
        val sortedByTypeList = getSortedByType(list)
        val todayProseList = getTodayProseList(getSortedByPopularList(list))
        val allProseList = getAllProseList(sortedByTypeList)
        viewModelScope.launch {
            proseListStateFlow.update { todayProseList + allProseList}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDefaultPage(){
        val todayProseList = getTodayProseList(emptyList())
        val allProseList = getAllProseList(emptyList())
        viewModelScope.launch {
            proseListStateFlow.update { todayProseList + allProseList}
        }
    }

    private fun getSortedByType(list : List<ProseVo>): List<ProseVo>{
        return when(sortTypeStateFlow.value){
            SortType.POPULAR -> getSortedByPopularList(list)
            SortType.RECENT -> getSortedByRecentList(list)
            SortType.AGE -> getSortedByAgeList(list)
        }
    }

    private fun getSortedByPopularList(list : List<ProseVo>) : List<ProseVo>{
        return list.sortedByDescending { it.commentCount + it.likeCount }
    }

    private fun getSortedByRecentList(list : List<ProseVo>) : List<ProseVo>{
        return list.sortedByDescending { it.createdAt }
    }

    private fun getSortedByAgeList(list : List<ProseVo>) : List<ProseVo>{
        return list.filter { it.age == UserInfo.info.age }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTodayProseList(list : List<ProseVo>) : List<HomeProseVo>{
        val yesterday = TimeFormatter.getYesterDay()
        val yesterdayList = list.filter { it.createdAt.split("/")[SPLIT_DATE] == yesterday }
        val homeProseVo = if(yesterdayList.isNotEmpty()){
            val todayProseList = if(yesterdayList.size > MAX_TODAY_PROSE) yesterdayList.take(MAX_TODAY_PROSE) else yesterdayList
            HomeProseVo(proseListVo = todayProseList, homeViewType = HomeViewType.TODAY_PROSE)
        }
        else {
            HomeProseVo(proseListVo = getDefaultProse(), homeViewType = HomeViewType.TODAY_PROSE)
        }
        return listOf(homeProseVo)

    }

    private fun getAllProseList(list : List<ProseVo>): List<HomeProseVo> {
        val homeAllProseList = mutableListOf<HomeProseVo>()
        list.forEach {
            homeAllProseList.add(
                HomeProseVo(allProseVo = it, homeViewType = HomeViewType.NORMAL_PROSE)
            )
        }

        return homeAllProseList.ifEmpty { emptyList() }
    }

    fun goToDetailPage(proseId : Int){
        emitEventFlow(HomeEvent.GoToDetailPageEvent(proseId, DetailType.PROSE))
    }

    fun onClickWriteProseBtn(){
        emitEventFlow(HomeEvent.GoToWriteProseEvent)
    }

    private fun getDefaultProse() : List<ProseVo>{
        val defaultProseVo = ProseVo(
            author = DEFAULT_AUTHOR,
            title = DEFAULT_TITLE,
            content = DEFAULT_CONTENT,
            proseId = DEVELOP_PROSE_ID

        )
        return listOf(defaultProseVo)
    }

    fun goToRecentSearchPage(){
        emitEventFlow(HomeEvent.GoToRecentSearchPageEvent)
    }

    fun onClickScrollUp(){
        emitEventFlow(HomeEvent.ScrollUpEvent)
    }

    fun goToDiscussion(){
        emitEventFlow(HomeEvent.GoToDiscussionEvent)
    }

    fun onClickBottomNavMy(){
        emitEventFlow(HomeEvent.GoToMyEvent)
    }
}