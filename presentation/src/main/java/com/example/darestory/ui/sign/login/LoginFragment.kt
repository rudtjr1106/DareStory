package com.example.darestory.ui.sign.login

import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentLoginBinding
import com.example.darestory.ui.common.InputDialog
import com.example.darestory.ui.main.MainActivity
import com.example.darestory.util.DareToast
import com.example.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginPageState, LoginViewModel>(
    FragmentLoginBinding::inflate
) {

    @Inject
    lateinit var inputDialog: InputDialog

    override val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            bindEditText()
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
            LoginEvent.GoToMainEvent -> goToMain()
            LoginEvent.ShowLoginErrorToastEvent -> showToastLoginError()
            LoginEvent.ShowErrorSendPwToastEvent -> showErrorSendPwToast()
            LoginEvent.ShowSuccessSendPwToastEvent -> showToastSuccessSendResetPw()
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
                viewModel.sendResetPasswordEmail(inputDialog.getEditText())
                inputDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                inputDialog.dismiss()
            }
            .show()
    }

    private fun goToMain(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun bindEditText(){
        binding.editTextEmailInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                return@setOnEditorActionListener true
            }
            false
        }

        binding.editTextPasswordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun showToastSuccessSendResetPw(){
        context?.let { DareToast.createToast(ToastType.COMPLETE, it, R.string.login_success_send_password_toast).show() }
    }

    private fun showErrorSendPwToast(){
        context?.let { DareToast.createToast(ToastType.ERROR, it, R.string.login_error_send_password_toast).show() }
    }

    private fun showToastLoginError(){
        context?.let { DareToast.createToast(ToastType.ERROR, it, R.string.login_error_toast).show() }
    }

}