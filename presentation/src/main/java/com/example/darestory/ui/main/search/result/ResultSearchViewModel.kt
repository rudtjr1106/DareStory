package com.example.darestory.ui.main.search.result

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.SearchType
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.SearchVo
import com.example.domain.usecase.home.GetProseSearchResultListUseCase
import com.example.domain.usecase.home.InsertRecentProseSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultSearchViewModel @Inject constructor(
    private val insertRecentProseSearchUseCase: InsertRecentProseSearchUseCase,
    private val getSearchResultListUseCase: GetProseSearchResultListUseCase
) : BaseViewModel<ResultSearchPageState>() {

    private val searchContentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val searchTypeStateFlow : MutableStateFlow<SearchType> = MutableStateFlow(SearchType.TITLE)
    private val searchResultProseListStateFlow : MutableStateFlow<List<ProseVo>> = MutableStateFlow(emptyList())
    private val isResultIsEmpty : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val searchContentIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val uiState: ResultSearchPageState = ResultSearchPageState(
        searchContentStateFlow,
        searchTypeStateFlow,
        searchResultProseListStateFlow.asStateFlow(),
        isResultIsEmpty.asStateFlow(),
        searchContentIsEmptyStateFlow.asStateFlow(),
    )

    private lateinit var detailType: DetailType
    fun setViewType(type : DetailType){
        detailType = type
    }

    fun getSearchedList(text : String){
        when(detailType){
            DetailType.PROSE -> {
                viewModelScope.launch {
                    searchContentStateFlow.update { text }
                    onSearchContentTextChangedAfter()
                    successInsertProseRecentSearch(text)
                }
            }
            DetailType.DISCUSSION -> {}
        }
    }


    fun insertProseRecentSearch(text : String){
        viewModelScope.launch {
            val result = insertRecentProseSearchUseCase(text)
            if(result) successInsertProseRecentSearch(text)
        }
    }

    fun insertProseRecentSearch(){
        viewModelScope.launch {
            val result = insertRecentProseSearchUseCase(searchContentStateFlow.value)
            if(result) successInsertProseRecentSearch(searchContentStateFlow.value)
        }
    }


    private fun successInsertProseRecentSearch(text : String){
        val request = SearchVo(
            text = text,
            type = searchTypeStateFlow.value
        )
        viewModelScope.launch {
            showLoading()
            val result = getSearchResultListUseCase(request)
            if(result.isNotEmpty()) successGetSearchedProseList(result) else successGetSearchedProseList(
                emptyList()
            )
            endLoading()
        }
    }

    private fun successGetSearchedProseList(list : List<ProseVo>){
        viewModelScope.launch {
            searchResultProseListStateFlow.update { list }
            updateIsResultFlow(searchResultProseListStateFlow.value.isEmpty())
        }
    }

    private fun updateIsResultFlow(isEmpty : Boolean){
        viewModelScope.launch {
            isResultIsEmpty.update { isEmpty }
        }
    }

    fun onClickBackBtn(){
        emitEventFlow(ResultSearchEvent.GoBackEvent)
    }

    fun onSearchContentTextChangedAfter(){
        viewModelScope.launch {
            searchContentIsEmptyStateFlow.update { searchContentStateFlow.value.isEmpty() }
        }
    }

    fun getSearchText() : String{
        return searchContentStateFlow.value
    }

    fun onClickTextSpinner(){
        emitEventFlow(ResultSearchEvent.OnClickSpinnerEvent)
    }
    fun updateSearchType(searchType: SearchType){
        viewModelScope.launch {
            searchTypeStateFlow.update { searchType }
            successInsertProseRecentSearch(searchContentStateFlow.value)
        }
    }
}