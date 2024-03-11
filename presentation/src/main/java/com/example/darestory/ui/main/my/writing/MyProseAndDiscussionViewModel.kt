package com.example.darestory.ui.main.my.writing

import androidx.lifecycle.viewModelScope
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.enums.BottomSheetMenuItemType
import com.example.domain.model.enums.DetailType
import com.example.domain.model.vo.MyOwnBookProseRequestVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.ResultSearchVo
import com.example.domain.usecase.my.DeleteMyOwnBookProseUseCase
import com.example.domain.usecase.my.GetMyDiscussionUseCase
import com.example.domain.usecase.my.GetMyOwnBookProseUseCase
import com.example.domain.usecase.my.GetMyProseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProseAndDiscussionViewModel @Inject constructor(
    private val getMyDiscussionUseCase: GetMyDiscussionUseCase,
    private val getMyProseUseCase: GetMyProseUseCase,
    private val getMyOwnBookProseUseCase : GetMyOwnBookProseUseCase,
    private val deleteMyOwnBookProseUseCase: DeleteMyOwnBookProseUseCase
) : BaseViewModel<MyProseAndDiscussionPageState>() {

    private val myWritingSearchStateFlow : MutableStateFlow<List<ResultSearchVo>> = MutableStateFlow(
        emptyList()
    )
    private val typeStateFlow : MutableStateFlow<DetailType> = MutableStateFlow(DetailType.PROSE)

    override val uiState: MyProseAndDiscussionPageState = MyProseAndDiscussionPageState(
        typeStateFlow.asStateFlow(),
        myWritingSearchStateFlow.asStateFlow(),
    )

    private var bookTitle = ""

    fun getMyData(type : DetailType, ownBookTitle : String){
        viewModelScope.launch {
            typeStateFlow.update { type }
            showLoading()
            when(type){
                DetailType.PROSE -> {
                    val result = getMyProseUseCase(Unit)
                    if(result.isNotEmpty()) successGetMyProseData(result) else emitEventFlow(MyProseAndDiscussionEvent.GoToProseWriteEvent)
                }
                DetailType.DISCUSSION -> {
                    val result = getMyDiscussionUseCase(Unit)
                    if(result.isNotEmpty()) successGetMyDiscussionData(result) else emitEventFlow(MyProseAndDiscussionEvent.GoToDiscussionWriteEvent)
                }
                DetailType.BOOK -> {
                    bookTitle = ownBookTitle
                    val result = getMyOwnBookProseUseCase(ownBookTitle)
                    if(result.isNotEmpty()) successGetMyOwnBookProseData(result) else successGetMyOwnBookProseData(
                        emptyList()
                    )
                }
            }
            endLoading()
        }
    }

    private fun successGetMyProseData(result : List<ProseVo>){
        val mutableList = mutableListOf<ResultSearchVo>()
        result.forEach {
            val vo = ResultSearchVo(
                proseVo = it,
                type = typeStateFlow.value
            )
            mutableList.add(vo)
        }
        updateMyWritingList(mutableList)
    }

    private fun successGetMyDiscussionData(result : List<DiscussionVo>){
        val mutableList = mutableListOf<ResultSearchVo>()
        result.forEach {
            val vo = ResultSearchVo(
                discussionVo = it,
                type = typeStateFlow.value
            )
            mutableList.add(vo)
        }
        updateMyWritingList(mutableList)
    }

    private fun successGetMyOwnBookProseData(result : List<ProseVo>){
        val mutableList = mutableListOf<ResultSearchVo>()
        result.forEach {
            val vo = ResultSearchVo(
                proseVo = it,
                type = typeStateFlow.value
            )
            mutableList.add(vo)
        }
        updateMyWritingList(mutableList)
    }

    private fun updateMyWritingList(list: List<ResultSearchVo>){
        viewModelScope.launch {
            myWritingSearchStateFlow.update { list }
        }
    }

    fun onClickImageMenuItemType(type : BottomSheetMenuItemType, item : ProseVo){
        when(type){
            BottomSheetMenuItemType.COMMENT_DELETE -> deleteMyOwnBookProse(item)
            else -> {}
        }
    }

    private fun deleteMyOwnBookProse(item :ProseVo){
        val request = MyOwnBookProseRequestVo(
            title = bookTitle,
            proseVo = item
        )

        viewModelScope.launch {
            val result = deleteMyOwnBookProseUseCase(request)
            if(result) reload()
        }
    }

    private fun reload(){
        getMyData(typeStateFlow.value, bookTitle)
    }

    fun onClickBack(){
        emitEventFlow(MyProseAndDiscussionEvent.GoToBackEvent)
    }
}