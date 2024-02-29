package com.example.darestory.ui.main.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentHomeBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.util.DareLog
import com.example.domain.model.enums.DetailType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter(object : HomeAdapter.HomeDelegate {
            override fun onClickProse(proseId: Int) {
                viewModel.goToDetailPage(proseId)
            }

            override fun onClickSortPopular() {
                DareLog.D("정렬 인기순")
            }

            override fun onClickSortRecent() {
                DareLog.D("정렬 최신순")
            }

            override fun onClickSortAge() {
                DareLog.D("정렬 나이순")
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = homeAdapter
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
        }
    }

    private fun goToDetailPage(proseId : Int, proseType : DetailType){
        val action = HomeFragmentDirections.actionHomeToDetail(proseId, proseType)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}