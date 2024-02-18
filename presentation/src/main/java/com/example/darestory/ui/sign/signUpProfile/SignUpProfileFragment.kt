package com.example.darestory.ui.sign.signUpProfile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.darestory.PageState
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentSignupProfileBinding
import com.example.darestory.util.DareLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpProfileFragment : BaseFragment<FragmentSignupProfileBinding, PageState.Default, SignUpProfileViewModel>(
    FragmentSignupProfileBinding::inflate
) {

    private val signUpProfileFragmentArgs : SignUpProfileFragmentArgs by navArgs()
    override val viewModel: SignUpProfileViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        DareLog.D(signUpProfileFragmentArgs.email + signUpProfileFragmentArgs.password)
        viewModel.updateTestInfo(signUpProfileFragmentArgs.email, signUpProfileFragmentArgs.password)

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {

                }
            }
        }
    }
}