package com.example.darestory.ui.main.my

import androidx.fragment.app.viewModels
import com.example.darestory.PageState
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDiscussionBinding
import com.example.darestory.databinding.FragmentMyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding, PageState.Default, MyViewModel>(
    FragmentMyBinding::inflate
) {

    override val viewModel: MyViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.test()
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