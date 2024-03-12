package com.darestory.presentation.ui.main.search.recent

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.base.BaseViewModel
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.usecase.discussion.GetRecentDiscussionSearchUseCase
import com.darestory.domain.usecase.discussion.InsertRecentDiscussionSearchUseCase
import com.darestory.domain.usecase.home.GetRecentProseSearchUseCase
import com.darestory.domain.usecase.home.InsertRecentProseSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val getRecentProseSearchUseCase: GetRecentProseSearchUseCase,
    private val insertRecentProseSearchUseCase: InsertRecentProseSearchUseCase,
    private val getRecentDiscussionSearchUseCase: GetRecentDiscussionSearchUseCase,
    private val insertRecentDiscussionSearchUseCase: InsertRecentDiscussionSearchUseCase
) : BaseViewModel<RecentSearchPageState>() {

    private val searchContentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val recentSearchedListStateFlow : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val searchContentIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val uiState: RecentSearchPageState = RecentSearchPageState(
        searchContentStateFlow,
        recentSearchedListStateFlow.asStateFlow(),
        searchContentIsEmptyStateFlow.asStateFlow(),
    )

    private var detailType by Delegates.notNull<DetailType>()

    fun getRecentSearchList(type : DetailType){
        detailType = type
        viewModelScope.launch {
            when (type) {
                DetailType.PROSE -> recentSearchedListStateFlow.update { getRecentProseSearchUseCase(Unit) }
                DetailType.DISCUSSION -> recentSearchedListStateFlow.update { getRecentDiscussionSearchUseCase(Unit) }
                DetailType.BOOK -> {}
            }
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
            val result = when(detailType){
                DetailType.PROSE -> insertRecentProseSearchUseCase(text)
                DetailType.DISCUSSION -> insertRecentDiscussionSearchUseCase(text)
                DetailType.BOOK -> false
            }
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