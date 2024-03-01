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

    fun onDestinationChanged(fragmentId: Int) {
        val isBottomNav = isBottomNavigationFragment(fragmentId)
        emitEventFlow(MainEvent.BottomNavigationVisibility(isBottomNav))
    }

    private fun isBottomNavigationFragment(fragmentId: Int): Boolean {
        return getBottomMenuIndex(fragmentId) != null
    }

    private fun getBottomMenuIndex(fragmentId: Int): Int? {
        return when (fragmentId) {
            R.id.menu_navigation_home -> {
                BottomNavigationItemType.HOME.idx
            }

            R.id.menu_navigation_discussion -> {
                BottomNavigationItemType.DISCUSSION.idx
            }

            R.id.menu_navigation_my -> {
                BottomNavigationItemType.MY.idx
            }

            else -> null
        }
    }

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