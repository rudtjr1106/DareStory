package com.example.darestory.ui.sign.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentLoginBinding
import com.example.darestory.ui.common.CommonDialog
import com.example.darestory.ui.common.InputDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, PageState.Default, LoginViewModel>(
    FragmentLoginBinding::inflate
) {

    @Inject
    lateinit var inputDialog: InputDialog

    override val viewModel: LoginViewModel by viewModels()

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
                    sortEvent(it as LoginEvent)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun sortEvent(event: LoginEvent){
        when(event){
            LoginEvent.SignUpTextClickEvent -> goToSignUp()
            LoginEvent.FindPasswordTextClickEvent -> showFindPasswordDialog()
            LoginEvent.LoginButtonClickEvent -> TODO()
        }
    }

    private fun goToSignUp(){
        val action = LoginFragmentDirections.actionLoginToSignupEmailPassword()
        findNavController().navigate(action)
    }

    private fun showFindPasswordDialog(){
        inputDialog
            .setTitle(R.string.input_email)
            .setPositiveButton(R.string.word_confirm) {
                //TODO 이메일 전송
                inputDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                inputDialog.dismiss()
            }
            .show()
    }

}