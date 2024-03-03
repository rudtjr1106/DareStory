package com.example.darestory.ui.main.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.TimeFormatter
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.HomeViewType
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.HomeProseVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.usecase.home.GetAllProseUseCase
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
        const val MAX_TODAY_PROSE = 3
        const val DEFAULT_AUTHOR = "조경석"
        const val DEFAULT_TITLE = "이 글은 터치가 되지 않습니다."
        const val DEFAULT_CONTENT = "개발자 조경석입니다."
        const val DEVELOP_PROSE_ID = -100
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
            if(result.isNotEmpty()) successGetAllProse(result) else showDefaultPage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSortType(type: SortType){
        viewModelScope.launch {
            sortTypeStateFlow.update { type }
            showLoading()
            val result = getAllProseUseCase(Unit)
            if(result.isNotEmpty()) successGetAllProse(result) else showDefaultPage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun successGetAllProse(list : List<ProseVo>){
        val sortedByTypeList = getSortedByType(list)
        val todayProseList = getTodayProseList(getSortedByPopularList(list))
        val allProseList = getAllProseList(sortedByTypeList)
        viewModelScope.launch {
            proseListStateFlow.update { todayProseList + allProseList}
            endLoading()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDefaultPage(){
        val todayProseList = getTodayProseList(emptyList())
        val allProseList = getAllProseList(emptyList())
        viewModelScope.launch {
            proseListStateFlow.update { todayProseList + allProseList}
            endLoading()
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
        val yesterdayList = list.filter {
            it.createdAt.split("/")[0] == yesterday
        }
        if(yesterdayList.isNotEmpty()){
            val todayProseList = if(yesterdayList.size > MAX_TODAY_PROSE) yesterdayList.take(MAX_TODAY_PROSE) else yesterdayList
            return listOf(
                HomeProseVo(proseListVo = todayProseList, homeViewType = HomeViewType.TODAY_PROSE)
            )
        }
        else {
            return listOf(
                HomeProseVo(proseListVo = getDefaultProse(), homeViewType = HomeViewType.TODAY_PROSE)
            )
        }

    }

    private fun getAllProseList(list : List<ProseVo>): List<HomeProseVo> {
        val homeAllProseList = mutableListOf<HomeProseVo>()
        list.forEach {
            homeAllProseList.add(
                HomeProseVo(allProseVo = it, homeViewType = HomeViewType.ALL_PROSE)
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
}