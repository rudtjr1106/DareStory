package com.example.darestory.ui.main.discussion

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDiscussionBinding
import com.example.darestory.ui.main.discussion.adapter.DiscussionAdapter
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.SortType
import com.example.domain.model.enums.WriteType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscussionFragment : BaseFragment<FragmentDiscussionBinding, DiscussionPageState, DiscussionViewModel>(
    FragmentDiscussionBinding::inflate
) {

    companion object{
        const val SHOW_SCROLL_UL_ICON_NUM = 20
    }

    override val viewModel: DiscussionViewModel by viewModels()

    private val discussionAdapter : DiscussionAdapter by lazy {
        DiscussionAdapter(object : DiscussionAdapter.DiscussionDelegate {
            override fun onClickSearch() {
                goToRecentSearch()
            }

            override fun onClickDiscussion(disId: Int) {
                goToDiscussionDetail(disId)
            }


            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClickSort(type: SortType) {
                viewModel.updateSortType(type)
                discussionAdapter.sortType = type
            }

            override fun onClickWriteDiscussion() {
                goToDiscussionWrite()
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerDiscussion.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = discussionAdapter
            }
            bindRecyclerListener()
            viewModel.getAllDiscussionList()

        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.discussionList.collect{
                    discussionAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as DiscussionEvent)
                }
            }
        }
    }

    private fun sortEvent(event: DiscussionEvent){
        when(event){
            DiscussionEvent.ScrollUpEvent -> scrollUp()
            DiscussionEvent.GoToHomeEvent -> goToHome()
            DiscussionEvent.GoToMyEvent -> goToMy()
        }
    }

    private fun bindRecyclerListener(){
        binding.apply {
            recyclerDiscussion.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun goToDiscussionDetail(discussionId : Int){
        val action = DiscussionFragmentDirections.actionDiscussionToDetail(detailId = discussionId, detailType = DetailType.DISCUSSION)
        findNavController().navigate(action)
    }

    private fun goToDiscussionWrite(){
        val action = DiscussionFragmentDirections.actionDiscussionToDiscussionWrite(discussionId = -1, discussionWriteType = WriteType.NEW)
        findNavController().navigate(action)
    }

    private fun goToRecentSearch(){
        val action = DiscussionFragmentDirections.actionDiscussionToRecentSearch(detailType = DetailType.DISCUSSION)
        findNavController().navigate(action)
    }

    private fun scrollUp(){
        binding.recyclerDiscussion.smoothScrollToPosition(0)
    }

    private fun goToHome(){
        val action = DiscussionFragmentDirections.actionDiscussionToHome()
        findNavController().navigate(action)
    }

    private fun goToMy(){

    }

    override fun onStart() {
        super.onStart()
    }
}