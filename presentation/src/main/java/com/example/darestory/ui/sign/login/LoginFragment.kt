package com.example.darestory.ui.sign.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.PageState
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentLoginBinding
import com.example.darestory.ui.common.CommonDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, PageState.Default, LoginViewModel>(
    FragmentLoginBinding::inflate
) {

    @Inject
    lateinit var commonDialog: CommonDialog

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
        commonDialog
            .setTitle("제목")
            .setDescription("내용입니다")
            .setPositiveButton("확인") {
                commonDialog.dismiss()
            }
            .setNegativeButton("취소") {
                commonDialog.dismiss()
            }
            .show()
    }

}