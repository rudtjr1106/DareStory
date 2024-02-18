package com.example.darestory.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darestory.Event
import com.example.darestory.PageState
import com.example.darestory.util.EventFlow
import com.example.darestory.util.MutableEventFlow
import com.example.darestory.util.asEventFlow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: PageState> : ViewModel() {

    abstract val uiState:STATE

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow: EventFlow<Event> = _eventFlow.asEventFlow()

    private val _showProgress = MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    val showProgress: SharedFlow<Boolean> = _showProgress.asSharedFlow()

    protected fun emitEventFlow(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

//    protected fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: ((T) -> Unit), individualErrorCallback: ((T?, IndividualError) -> Unit)? = null) {
//        uiInspector.inspectUiState(uiState,succeedCallback, individualErrorCallback)
//    }
}