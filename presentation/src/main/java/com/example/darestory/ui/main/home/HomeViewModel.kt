package com.example.darestory.ui.main.home

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.DareLog
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
    private val getAllProseUseCase: GetAllProseUseCase
) : BaseViewModel<HomePageState>() {

    private val proseListStateFlow: MutableStateFlow<List<HomeProseVo>> = MutableStateFlow(emptyList())

    override val uiState: HomePageState = HomePageState(
        proseListStateFlow.asStateFlow()
    )

    fun getAllProse(){
        viewModelScope.launch {
            val result = getAllProseUseCase(Unit)
            if(result.isNotEmpty()) successGetAllProse(result)
        }
    }

    private fun successGetAllProse(list : List<ProseVo>){
        DareLog.D(list.toString())
        val today = listOf(
            HomeProseVo(
                proseListVo = list,
                homeViewType = HomeViewType.TODAY_PROSE
            )
        )
        viewModelScope.launch {
            proseListStateFlow.update { today }
        }
    }
}