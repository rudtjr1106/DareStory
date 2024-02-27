package com.example.darestory.ui.main

import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun onBottomNavigationItemSelected(fragmentId : Int){
        when(fragmentId){
            R.id.menu_navigation_home -> {
                emitEventFlow(MainEvent.NavigateHome)
            }

            R.id.menu_navigation_discussion -> {
                emitEventFlow(MainEvent.NavigateDiscussion)
            }

            R.id.menu_navigation_my -> {
                emitEventFlow(MainEvent.NavigateMy)
            }
        }
    }

}