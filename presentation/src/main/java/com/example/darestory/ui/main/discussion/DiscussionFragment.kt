package com.example.darestory.ui.main.discussion

import androidx.fragment.app.viewModels
import com.example.darestory.PageState
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDiscussionBinding
import com.example.darestory.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscussionFragment : BaseFragment<FragmentDiscussionBinding, PageState.Default, DiscussionViewModel>(
    FragmentDiscussionBinding::inflate
) {

    override val viewModel: DiscussionViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}