package com.example.darestory.ui.main.search.recent

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.vo.RecentSearchVo
import com.example.domain.usecase.home.GetRecentProseSearchUseCase
import com.example.domain.usecase.home.InsertRecentProseSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val getRecentProseSearchUseCase: GetRecentProseSearchUseCase,
    private val insertRecentProseSearchUseCase: InsertRecentProseSearchUseCase
) : BaseViewModel<RecentSearchPageState>() {

    private val searchContentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val recentSearchedListStateFlow : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val searchContentIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val uiState: RecentSearchPageState = RecentSearchPageState(
        searchContentStateFlow,
        recentSearchedListStateFlow.asStateFlow(),
        searchContentIsEmptyStateFlow.asStateFlow(),
    )

    fun test1(){
        viewModelScope.launch {
            getRecentProseSearchUseCase(Unit)
        }
    }

    fun test2(){
        viewModelScope.launch {
            insertRecentProseSearchUseCase(RecentSearchVo())
        }
    }

    fun onSearchContentTextChangedAfter(){
        viewModelScope.launch {
            searchContentIsEmptyStateFlow.update { searchContentStateFlow.value.isEmpty() }
        }
    }
}