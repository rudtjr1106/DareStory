package com.darestory.presentation.ui.main.my.readOrOwnBook

import androidx.lifecycle.viewModelScope
import com.darestory.presentation.base.BaseViewModel
import com.darestory.presentation.util.SelectedBook
import com.darestory.presentation.util.SelectedMyOwnBook
import com.darestory.domain.model.enums.ReadOrOwnType
import com.darestory.domain.model.vo.BookVo
import com.darestory.domain.model.vo.MyBookVo
import com.darestory.domain.model.vo.MyReadOrOwnBookVo
import com.darestory.domain.usecase.my.AddMyReadBookUseCase
import com.darestory.domain.usecase.my.GetMyOwnBookUseCase
import com.darestory.domain.usecase.my.GetMyReadBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReadOrOwnBookViewModel @Inject constructor(
    private val getMyOwnBookUseCase: GetMyOwnBookUseCase,
    private val getMyReadBookUseCase: GetMyReadBookUseCase,
    private val addMyReadBookUseCase: AddMyReadBookUseCase
) : BaseViewModel<MyReadOrOwnBookPageState>() {

    private val typeStateFlow: MutableStateFlow<ReadOrOwnType> =
        MutableStateFlow(ReadOrOwnType.READ_BOOK)
    private val myReadOrOwnBookListStateFlow: MutableStateFlow<List<MyReadOrOwnBookVo>> =
        MutableStateFlow(
            emptyList()
        )

    override val uiState: MyReadOrOwnBookPageState = MyReadOrOwnBookPageState(
        typeStateFlow.asStateFlow(),
        myReadOrOwnBookListStateFlow.asStateFlow()
    )

    fun getMyData(type: ReadOrOwnType) {
        viewModelScope.launch {
            typeStateFlow.update { type }
            when (type) {
                ReadOrOwnType.READ_BOOK -> {
                    val result = getMyReadBookUseCase(Unit)
                    if (result.isNotEmpty()) successGetMyReadBookData(result)
                }

                ReadOrOwnType.SELECT_OWN_BOOK,
                ReadOrOwnType.OWN_BOOK -> {
                    val result = getMyOwnBookUseCase(Unit)
                    if (result.isNotEmpty()) successGetMyOwnBookData(result)
                }
            }
        }
    }


    private fun successGetMyReadBookData(result: List<BookVo>) {
        val mutableList = mutableListOf<MyReadOrOwnBookVo>()
        result.forEach {
            val vo = MyReadOrOwnBookVo(
                bookVo = it,
                type = typeStateFlow.value
            )
            mutableList.add(vo)
        }
        updateMyReadOrOwnBookList(mutableList)
    }

    private fun successGetMyOwnBookData(result: List<MyBookVo>) {
        val mutableList = mutableListOf<MyReadOrOwnBookVo>()
        result.forEach {
            val vo = MyReadOrOwnBookVo(
                myBookVo = it,
                type = typeStateFlow.value
            )
            mutableList.add(vo)
        }
        updateMyReadOrOwnBookList(mutableList)
    }

    private fun updateMyReadOrOwnBookList(list: List<MyReadOrOwnBookVo>) {
        viewModelScope.launch {
            myReadOrOwnBookListStateFlow.update { list }
        }
    }

    fun onClickBack() {
        emitEventFlow(MyReadOrOwnBookEvent.GoToBackEvent)
    }

    fun onClickAddBtn() {
        when (typeStateFlow.value) {
            ReadOrOwnType.READ_BOOK -> emitEventFlow(MyReadOrOwnBookEvent.GoToResultSearchEvent)
            ReadOrOwnType.OWN_BOOK -> emitEventFlow(MyReadOrOwnBookEvent.GoToMyOwnBookWriteEvent)
            ReadOrOwnType.SELECT_OWN_BOOK -> emitEventFlow(MyReadOrOwnBookEvent.GoToMyOwnBookWriteEvent)
        }
    }

    fun addMyReadBook(){
        viewModelScope.launch {
            val result = addMyReadBookUseCase(SelectedBook.book)
            if(result) successAddMyReadBook() else emitEventFlow(MyReadOrOwnBookEvent.ErrorUploadEvent)
        }
    }

    private fun successAddMyReadBook(){
        SelectedBook.updateInfo(BookVo())
        reload()
    }

    fun updateSelectedBook(item : MyBookVo){
        SelectedMyOwnBook.updateInfo(item)
        emitEventFlow(MyReadOrOwnBookEvent.GoToBackEvent)
    }
    private fun reload(){
        getMyData(typeStateFlow.value)
    }
}