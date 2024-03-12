package com.darestory.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darestory.presentation.Event
import com.darestory.presentation.PageState
import com.darestory.presentation.util.EventFlow
import com.darestory.presentation.util.MutableEventFlow
import com.darestory.presentation.util.asEventFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: PageState> : ViewModel() {

    abstract val uiState:STATE

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow: EventFlow<Event> = _eventFlow.asEventFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected fun emitEventFlow(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    protected fun showLoading(){
        _isLoading.value = true
    }

    protected fun endLoading(){
        _isLoading.value = false
    }
}