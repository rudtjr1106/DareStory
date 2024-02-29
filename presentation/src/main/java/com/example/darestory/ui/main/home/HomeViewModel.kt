package com.example.darestory.ui.main.home

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.HomeViewType
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
    }

    private val proseListStateFlow: MutableStateFlow<List<HomeProseVo>> = MutableStateFlow(emptyList())

    override val uiState: HomePageState = HomePageState(
        proseListStateFlow.asStateFlow()
    )

    fun getAllProse(){
        viewModelScope.launch {

        }
        viewModelScope.launch {
            showLoading()
            val result = getAllProseUseCase(Unit)
            if(result.isNotEmpty()) successGetAllProse(result)
        }
    }

    private fun successGetAllProse(list : List<ProseVo>){
        val sortedByPopularList = getSortedByPopularList(list)
        val todayProseList = getTodayProseList(sortedByPopularList)
        val allProseList = getAllProseList(sortedByPopularList)
        viewModelScope.launch {
            proseListStateFlow.update { todayProseList + allProseList}
            endLoading()
        }
    }

    private fun getSortedByPopularList(list : List<ProseVo>) : List<ProseVo>{
        return list.sortedByDescending { it.commentCount + it.likeCount }
    }

    private fun getTodayProseList(list : List<ProseVo>) : List<HomeProseVo>{
        //TODO 어제 날짜 기준 가져오기
//        val now = LocalDate.now() // 현재 날짜를 가져옵니다.
//        val yesterday = now.minusDays(1) // 어제 날짜를 계산합니다.
//
//        val sortedProseList: List<ProseVo> = // 정렬된 리스트를 가정합니다.
//
//        val yesterdayProseList = sortedProseList.filter { prose ->
//            val createdAtDate = LocalDate.parse(prose.createdAt, DateTimeFormatter.ISO_DATE)
//            createdAtDate == yesterday
//        }
        val todayProseList = if(list.size > MAX_TODAY_PROSE) list.take(MAX_TODAY_PROSE) else list

        return listOf(HomeProseVo(
                    proseListVo = todayProseList,
                    homeViewType = HomeViewType.TODAY_PROSE))

    }

    private fun getAllProseList(list : List<ProseVo>): List<HomeProseVo> {
        val homeAllProseList = mutableListOf<HomeProseVo>()
        list.forEach {
            homeAllProseList.add(
                HomeProseVo(allProseVo = it, homeViewType = HomeViewType.ALL_PROSE)
            )
        }

        return homeAllProseList
    }

    fun goToDetailPage(proseId : Int){
        emitEventFlow(HomeEvent.GoToDetailPageEvent(proseId, DetailType.PROSE))
    }
}