package com.example.darestory.ui.sign.signUpEmailPassword

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentSignupEmailPasswordBinding
import com.example.darestory.ui.common.spinner.SpinnerDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpEmailPasswordFragment : BaseFragment<FragmentSignupEmailPasswordBinding, SignUpEmailPasswordPageState, SignUpEmailPasswordViewModel>(
    FragmentSignupEmailPasswordBinding::inflate
), SpinnerDialog.OnItemSelectedListener {

    @Inject
    lateinit var spinnerDialog: SpinnerDialog

    override val viewModel: SignUpEmailPasswordViewModel by viewModels()

    companion object{
        const val PASSWORD_TERMS = 8
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.password.collect{
                    if(it.isNotEmpty()) checkPasswordTerms(it.length >= PASSWORD_TERMS)
                }
            }

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
            is SignUpEmailPasswordEvent.OnClickSpinner -> onClickSpinner()
        }
    }

    private fun goToNextPage(email : String, password : String){
        val action = SignUpEmailPasswordFragmentDirections.actionEmailPasswordToProfile(email = email, password = password)
        findNavController().navigate(action)
    }

    private fun onClickSpinner(){
        val items = resources.getStringArray(R.array.email_domain_array)
        spinnerDialog.setItems(items.toList())
        spinnerDialog.setOnItemSelectedListener(this)
        spinnerDialog.show()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun checkPasswordTerms(canUse : Boolean){
        binding.apply {
            imagePasswordMore8.visibility = View.VISIBLE
            if(canUse) {
                imagePasswordMore8.setBackgroundResource(R.drawable.ic_check_check)
                textTermsPassword.setTextColor(ContextCompat.getColor(requireContext(), R.color.check))
            }
            else{
                imagePasswordMore8.setBackgroundResource(R.drawable.ic_close_error)
                textTermsPassword.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
            }
        }
    }

    override fun onItemSelected(itemName: String) {
        viewModel.onSelectedEmailDomain(itemName)
    }
}