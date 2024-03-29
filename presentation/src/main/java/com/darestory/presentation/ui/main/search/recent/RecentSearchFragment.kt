package com.darestory.presentation.ui.main.search.recent

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentRecentSearchBinding
import com.darestory.presentation.ui.main.search.recent.adapter.RecentSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentSearchFragment : BaseFragment<FragmentRecentSearchBinding, RecentSearchPageState, RecentSearchViewModel>(
    FragmentRecentSearchBinding::inflate
) {

    override val viewModel: RecentSearchViewModel by viewModels()
    private val recentSearchFragmentArgs : RecentSearchFragmentArgs by navArgs()

    private val recentSearchAdapter : RecentSearchAdapter by lazy {
        RecentSearchAdapter(object : RecentSearchAdapter.RecentSearchDelegate {
            override fun onClickItem(recent: String) {
                viewModel.setSearchContent(recent)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerRecentSearch.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recentSearchAdapter
            }
            bindEditTextKeyboard()
            viewModel.getRecentSearchList(recentSearchFragmentArgs.detailType)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.recentSearchedList.collect{
                    recentSearchAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as RecentSearchEvent)
                }
            }
        }
    }

    private fun sortEvent(event : RecentSearchEvent){
        when(event){
            RecentSearchEvent.GoBackEvent -> findNavController().popBackStack()
            is RecentSearchEvent.GoResultEvent -> goToResultPage(event.searchText)
        }
    }

    private fun bindEditTextKeyboard(){
        binding.apply {
            editTextSearch.apply {
                setOnEditorActionListener { _, keyCode, keyEvent ->
                    if(keyCode == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.insertRecentSearch(text.toString())
                        true
                    }else false
                }
            }
        }

    }

    private fun goToResultPage(searchedText : String){
        val action = RecentSearchFragmentDirections.actionRecentToResultSearch(recentSearchFragmentArgs.detailType, searchedText)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecentSearchList(recentSearchFragmentArgs.detailType)
    }

    override fun onStart() {
        super.onStart()
    }
}