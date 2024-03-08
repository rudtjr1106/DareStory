package com.example.darestory.ui.main.my

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentMyBinding
import com.example.darestory.ui.common.InputDialog
import com.example.domain.model.enums.DetailType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding, MyPageState, MyViewModel>(
    FragmentMyBinding::inflate
) {
    @Inject
    lateinit var inputDialog: InputDialog

    override val viewModel: MyViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.getMyInfo()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as MyEvent)
                }
            }
        }
    }

    private fun sortEvent(event: MyEvent){
        when(event){
            is MyEvent.GoToMyProseAndDiscussion -> goToMyProseAndDiscussion(event.type)
        }
    }

    private fun goToMyProseAndDiscussion(type : DetailType){
        val action = MyFragmentDirections.actionMyToMyProseAndDiscussion(type)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}