package com.example.darestory.ui.main.search.result

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.SearchType
import com.example.domain.model.vo.BookVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.ResultSearchVo
import com.example.domain.model.vo.SearchVo
import com.example.domain.usecase.book.GetSearchBookUseCase
import com.example.domain.usecase.discussion.GetDiscussionSearchResultListUseCase
import com.example.domain.usecase.discussion.InsertRecentDiscussionSearchUseCase
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
    private val insertRecentDiscussionSearchUseCase: InsertRecentDiscussionSearchUseCase,
    private val getSearchProseResultListUseCase: GetProseSearchResultListUseCase,
    private val getSearchDiscussionResultListUseCase: GetDiscussionSearchResultListUseCase,
    private val getSearchBookUseCase: GetSearchBookUseCase
) : BaseViewModel<ResultSearchPageState>() {

    private val searchContentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val searchTypeStateFlow : MutableStateFlow<SearchType> = MutableStateFlow(SearchType.TITLE)
    private val searchResultListStateFlow : MutableStateFlow<List<ResultSearchVo>> = MutableStateFlow(emptyList())
    private val isResultIsEmpty : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val searchContentIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val uiState: ResultSearchPageState = ResultSearchPageState(
        searchContentStateFlow,
        searchTypeStateFlow,
        searchResultListStateFlow.asStateFlow(),
        isResultIsEmpty.asStateFlow(),
        searchContentIsEmptyStateFlow.asStateFlow(),
    )

    private lateinit var detailType: DetailType
    fun setViewType(type : DetailType){
        detailType = type
    }

    fun getSearchedList(text : String){
        viewModelScope.launch {
            searchContentStateFlow.update { text }
            onSearchContentTextChangedAfter()
            when (detailType) {
                DetailType.PROSE -> successInsertProseRecentSearch(text)
                DetailType.DISCUSSION -> successInsertDiscussionRecentSearch(text)
                DetailType.BOOK -> getBookSearchList()
            }
        }
    }

    fun insertProseRecentSearch(){
        viewModelScope.launch {
            val result = insertRecentProseSearchUseCase(searchContentStateFlow.value)
            if(result) successInsertProseRecentSearch(searchContentStateFlow.value)
        }
    }

    fun insertDiscussionRecentSearch(){
        viewModelScope.launch {
            val result = insertRecentDiscussionSearchUseCase(searchContentStateFlow.value)
            if(result) successInsertDiscussionRecentSearch(searchContentStateFlow.value)
        }
    }


    private fun successInsertProseRecentSearch(text : String){
        val request = SearchVo(
            text = text,
            type = searchTypeStateFlow.value
        )
        viewModelScope.launch {
            showLoading()
            val result = getSearchProseResultListUseCase(request)
            if(result.isNotEmpty()) successGetSearchedProseList(result) else successGetSearchedProseList(
                emptyList()
            )
            endLoading()
        }
    }

    private fun successInsertDiscussionRecentSearch(text : String){
        val request = SearchVo(
            text = text,
            type = searchTypeStateFlow.value
        )
        viewModelScope.launch {
            showLoading()
            val result = getSearchDiscussionResultListUseCase(request)
            if(result.isNotEmpty()) successGetSearchedDiscussionList(result) else successGetSearchedDiscussionList(
                emptyList()
            )
            endLoading()
        }
    }

    fun getBookSearchList(){
        if(searchContentStateFlow.value.isEmpty()){
            successGetSearchedBookList(emptyList())
        }
        else{
            viewModelScope.launch {
                showLoading()
                val result = getSearchBookUseCase(searchContentStateFlow.value)
                if(result.items.isNotEmpty()) successGetSearchedBookList(result.items) else successGetSearchedBookList(
                    emptyList()
                )
                endLoading()
            }
        }
    }

    private fun successGetSearchedProseList(list : List<ProseVo>){
        val searchList = mutableListOf<ResultSearchVo>()
        list.forEach {
            searchList.add(
                ResultSearchVo(proseVo = it, type = DetailType.PROSE)
            )
        }
        viewModelScope.launch {
            searchResultListStateFlow.update { searchList }
            updateIsResultFlow(list.isEmpty())
        }
    }

    private fun successGetSearchedDiscussionList(list : List<DiscussionVo>){
        val searchList = mutableListOf<ResultSearchVo>()
        list.forEach {
            searchList.add(
                ResultSearchVo(discussionVo = it, type = DetailType.DISCUSSION)
            )
        }
        viewModelScope.launch {
            searchResultListStateFlow.update { searchList }
            updateIsResultFlow(list.isEmpty())
        }
    }

    private fun successGetSearchedBookList(list : List<BookVo>){
        val searchList = mutableListOf<ResultSearchVo>()
        list.forEach {
            searchList.add(
                ResultSearchVo(bookVo = it, type = DetailType.BOOK)
            )
        }
        viewModelScope.launch {
            searchResultListStateFlow.update { searchList }
            updateIsResultFlow(list.isEmpty())
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