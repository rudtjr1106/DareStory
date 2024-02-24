package com.example.darestory.ui.sign.certifyEmail

import androidx.fragment.app.viewModels
import com.example.darestory.PageState
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentCertifyEmailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CertifyEmailFragment : BaseFragment<FragmentCertifyEmailBinding, PageState.Default, CertifyEmailViewModel>(
    FragmentCertifyEmailBinding::inflate
) {


    override val viewModel: CertifyEmailViewModel by viewModels()

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