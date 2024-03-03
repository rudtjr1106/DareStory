package com.example.darestory.ui.main.home

import android.graphics.Color
import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentHomeBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.util.DareLog
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.ProseWriteType
import com.example.domain.model.enums.SortType
import com.google.android.material.color.ColorContrast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter(object : HomeAdapter.HomeDelegate {
            override fun onClickSearch() {
                viewModel.goToRecentSearchPage()
            }

            override fun onClickProse(proseId: Int) {
                viewModel.goToDetailPage(proseId)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClickSort(type: SortType) {
                viewModel.updateSortType(type)
                homeAdapter.sortType = type
            }

            override fun onClickWriteProse() {
                viewModel.onClickWriteProseBtn()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = homeAdapter
            }

            swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.purple_600))
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                viewModel.getAllProse()
            }

            viewModel.getAllProse()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.proseList.collect{
                    homeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as HomeEvent)
                }
            }
        }
    }

    private fun sortEvent(event : HomeEvent){
        when(event){
            is HomeEvent.GoToDetailPageEvent -> goToDetailPage(event.proseId, event.proseType)
            is HomeEvent.GoToWriteProseEvent -> goToWriteProsePage()
            is HomeEvent.GoToRecentSearchPageEvent -> goToRecentSearchPage()
        }
    }

    private fun goToDetailPage(proseId : Int, proseType : DetailType){
        val action = HomeFragmentDirections.actionHomeToDetail(proseId, proseType)
        findNavController().navigate(action)
    }

    private fun goToWriteProsePage(){
        val action = HomeFragmentDirections.actionHomeToProseWrite(proseId = -1, proseWriteType = ProseWriteType.NEW)
        findNavController().navigate(action)
    }

    private fun goToRecentSearchPage(){
        val action = HomeFragmentDirections.actionHomeToProseRecentSearch()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}