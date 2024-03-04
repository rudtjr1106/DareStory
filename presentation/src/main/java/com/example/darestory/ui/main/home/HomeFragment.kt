package com.example.darestory.ui.main.home

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentHomeBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.ProseWriteType
import com.example.domain.model.enums.SortType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    companion object{
        const val SHOW_SCROLL_UL_ICON_NUM = 20
    }

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
            bindRecyclerListener()

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
            is HomeEvent.ScrollUpEvent -> scrollUp()
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

    private fun bindRecyclerListener(){
        binding.apply {
            recyclerHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition >= SHOW_SCROLL_UL_ICON_NUM) {
                        binding.imageBtnScrollUp.visibility = View.VISIBLE
                    }
                    else{
                        binding.imageBtnScrollUp.visibility = View.GONE
                    }
                }
            })

        }
    }

    private fun scrollUp(){
        binding.recyclerHome.smoothScrollToPosition(0)
    }

    override fun onStart() {
        super.onStart()
    }
}