package com.example.darestory.ui.main.discussion

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDiscussionBinding
import com.example.darestory.ui.main.discussion.adapter.DiscussionAdapter
import com.example.domain.model.enums.SortType
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

            }

            override fun onClickDiscussion(disId: Int) {
            }


            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClickSort(type: SortType) {
                viewModel.updateSortType(type)
                discussionAdapter.sortType = type
            }

            override fun onClickWriteDiscussion() {
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

                }
            }
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

    private fun scrollUp(){
        binding.recyclerDiscussion.smoothScrollToPosition(0)
    }

    override fun onStart() {
        super.onStart()
    }
}