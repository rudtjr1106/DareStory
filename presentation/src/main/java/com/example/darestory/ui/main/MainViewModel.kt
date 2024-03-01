package com.example.darestory.ui.main

import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseViewModel
import com.example.domain.model.enums.BottomNavigationItemType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")
}