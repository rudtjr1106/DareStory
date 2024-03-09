package com.example.darestory.ui.main.my.notice.detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentNoticeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoticeDetailFragment : BaseFragment<FragmentNoticeDetailBinding, NoticeDetailPageState, NoticeDetailViewModel>(
    FragmentNoticeDetailBinding::inflate
) {
    override val viewModel: NoticeDetailViewModel by viewModels()

    private val noticeDetailFragmentArgs : NoticeDetailFragmentArgs by navArgs()
    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.getNoticeDetail(noticeDetailFragmentArgs.noticeId)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as NoticeDetailEvent)
                }
            }
        }
    }

    private fun sortEvent(event: NoticeDetailEvent){
        when(event){
            NoticeDetailEvent.GoToBackEvent -> findNavController().popBackStack()
            NoticeDetailEvent.ShowWriterEvent -> binding.textMe.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
    }
}