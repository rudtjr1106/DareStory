package com.darestory.presentation.ui.main.my.notice

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentNoticeBinding
import com.darestory.presentation.ui.main.my.notice.adapter.NoticeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticePageState, NoticeViewModel>(
    FragmentNoticeBinding::inflate
) {
    override val viewModel: NoticeViewModel by viewModels()

    private val noticeAdapter = NoticeAdapter(object : NoticeAdapter.NoticeDelegate{
        override fun onClickNotice(noticeId: Int) {
            goToNoticeDetail(noticeId)
        }
    })

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerDetail.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = noticeAdapter
            }
            viewModel.getNotice()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.noticeList.collect{
                    noticeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as NoticeEvent)
                }
            }
        }
    }

    private fun sortEvent(event: NoticeEvent){
        when(event){
            NoticeEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun goToNoticeDetail(noticeId : Int){
        val action = NoticeFragmentDirections.actionNoticeToDetail(noticeId)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}