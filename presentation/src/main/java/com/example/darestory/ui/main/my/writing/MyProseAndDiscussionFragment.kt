package com.example.darestory.ui.main.my.writing

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentMyProseAndDiscussionBinding
import com.example.darestory.ui.main.discussion.adapter.DiscussionAdapter
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.search.result.adapter.ResultSearchAdapter
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.BookVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyProseAndDiscussionFragment : BaseFragment<FragmentMyProseAndDiscussionBinding, MyProseAndDiscussionPageState, MyProseAndDiscussionViewModel>(
    FragmentMyProseAndDiscussionBinding::inflate
) {

    override val viewModel: MyProseAndDiscussionViewModel by viewModels()

    private val myProseAndDiscussionFragmentArgs : MyProseAndDiscussionFragmentArgs by navArgs()

    private val resultSearchAdapter : ResultSearchAdapter by lazy {
        ResultSearchAdapter(object : HomeAdapter.HomeDelegate {
            override fun onClickSearch() {}
            override fun onClickProse(proseId: Int) {
                goToDetail(proseId)
            }
            override fun onClickSort(type: SortType) {}
            override fun onClickWriteProse() {}
        },

            object : ResultSearchAdapter.ResultSearchDelegate{
                override fun onClickBook(item: BookVo) { }
            },
            object : DiscussionAdapter.DiscussionDelegate{
                override fun onClickSearch() {}
                override fun onClickDiscussion(disId: Int) {
                    goToDetail(disId)
                }
                override fun onClickSort(type: SortType) {}
                override fun onClickWriteDiscussion() {}

            })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerDetail.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = resultSearchAdapter
            }
            setTitle(myProseAndDiscussionFragmentArgs.discussionWriteType)
            viewModel.getMyData(myProseAndDiscussionFragmentArgs.discussionWriteType)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.myWritingList.collect{
                    resultSearchAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as MyProseAndDiscussionEvent)
                }
            }
        }
    }

    private fun sortEvent(event: MyProseAndDiscussionEvent){
        when(event){
            MyProseAndDiscussionEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun setTitle(type : DetailType){
        binding.apply {
            val textRes = when(type){
                DetailType.PROSE -> R.string.my_prose
                DetailType.DISCUSSION -> R.string.my_discussion
                DetailType.BOOK -> R.string.my_prose
            }
            textMyWriteType.text = getString(textRes)
        }
    }

    private fun goToDetail(id : Int){
        val action = MyProseAndDiscussionFragmentDirections.actionMyProseAndDiscussionToDetail(id, myProseAndDiscussionFragmentArgs.discussionWriteType)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}