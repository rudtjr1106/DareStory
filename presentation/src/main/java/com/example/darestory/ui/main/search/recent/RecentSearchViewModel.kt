package com.example.darestory.ui.main.search.recent

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.DareLog
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

    fun getRecentSearchList(){
        viewModelScope.launch {
            recentSearchedListStateFlow.update { getRecentProseSearchUseCase(Unit) }
        }
    }

    fun setSearchContent(text : String){
        viewModelScope.launch {
            searchContentStateFlow.update { text }
            onSearchContentTextChangedAfter()
            insertRecentSearch(text)
        }
    }

    fun insertRecentSearch(text : String){
        viewModelScope.launch {
            val result = insertRecentProseSearchUseCase(text)
            if(result) successInsertRecentSearch(text)
        }
    }

    private fun successInsertRecentSearch(text : String){
        emitEventFlow(RecentSearchEvent.GoResultEvent(text))
    }

    fun onClickBackBtn(){
        emitEventFlow(RecentSearchEvent.GoBackEvent)
    }

    fun onSearchContentTextChangedAfter(){
        viewModelScope.launch {
            searchContentIsEmptyStateFlow.update { searchContentStateFlow.value.isEmpty() }
        }
    }
}