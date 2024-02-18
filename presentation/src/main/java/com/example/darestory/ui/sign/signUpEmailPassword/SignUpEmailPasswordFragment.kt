package com.example.darestory.ui.sign.signUpEmailPassword

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentSignupEmailPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpEmailPasswordFragment : BaseFragment<FragmentSignupEmailPasswordBinding, SignUpEmailPasswordPageState, SignUpEmailPasswordViewModel>(
    FragmentSignupEmailPasswordBinding::inflate
) {
    override val viewModel: SignUpEmailPasswordViewModel by viewModels()

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
                    sortEvent(it as SignUpEmailPasswordEvent)
                }
            }
        }
    }

    private fun sortEvent(event : SignUpEmailPasswordEvent){
        when(event){
            is SignUpEmailPasswordEvent.GoBack -> findNavController().popBackStack()
            is SignUpEmailPasswordEvent.GoToNext -> goToNextPage(event.email, event.password)
        }
    }

    private fun goToNextPage(email : String, password : String){
        val action = SignUpEmailPasswordFragmentDirections.actionEmailPasswordToProfile(email = email, password = password)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}