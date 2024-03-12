package com.darestory.presentation.ui.sign.signUpProfile

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentSignupProfileBinding
import com.darestory.presentation.ui.common.spinner.SpinnerDialog
import com.darestory.domain.model.error.NickNameError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpProfileFragment : BaseFragment<FragmentSignupProfileBinding, SignUpProfilePageState, SignUpProfileViewModel>(
    FragmentSignupProfileBinding::inflate
), SpinnerDialog.OnItemSelectedListener {

    @Inject
    lateinit var spinnerDialog: SpinnerDialog

    private val signUpProfileFragmentArgs : SignUpProfileFragmentArgs by navArgs()
    override val viewModel: SignUpProfileViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            bindEditText()
            viewModel.getAllNickName()
        }
    }

    override fun initStates() {
        super.initStates()

        viewModel.updateInfo(signUpProfileFragmentArgs.email, signUpProfileFragmentArgs.password)

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.nicknameError.collect{
                    checkNickNameTerms(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as SignUpProfileEvent)
                }
            }
        }
    }

    private fun sortEvent(event : SignUpProfileEvent){
        when(event){
            is SignUpProfileEvent.GoBack -> findNavController().popBackStack()
            is SignUpProfileEvent.OnClickSpinner -> onClickSpinner()
            is SignUpProfileEvent.GoCertifyEmail -> goToCertifyEmailFragment(event.email)
        }
    }

    private fun onClickSpinner(){
        val items = resources.getStringArray(R.array.age_array)
        spinnerDialog.setItems(items.toList())
        spinnerDialog.setOnItemSelectedListener(this)
        spinnerDialog.show()
    }

    override fun onItemSelected(itemName: String) {
        viewModel.onSelectedAge(itemName)
    }

    private fun checkNickNameTerms(state : NickNameError){
        binding.apply {
            imageNicknameTermsDuplicate.visibility = View.VISIBLE
            imageNicknameTermsLength.visibility = View.VISIBLE
            when(state){
                NickNameError.DuplicateError -> {
                    imageNicknameTermsDuplicate.setBackgroundResource(R.drawable.ic_close_error)
                    textNicknameTermsDuplicate.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                }
                NickNameError.LengthError -> {
                    imageNicknameTermsLength.setBackgroundResource(R.drawable.ic_close_error)
                    textNicknameTermsLength.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                }
                NickNameError.NoError -> {
                    imageNicknameTermsLength.setBackgroundResource(R.drawable.ic_check_check)
                    textNicknameTermsLength.setTextColor(ContextCompat.getColor(requireContext(), R.color.check))
                    imageNicknameTermsDuplicate.setBackgroundResource(R.drawable.ic_check_check)
                    textNicknameTermsDuplicate.setTextColor(ContextCompat.getColor(requireContext(), R.color.check))
                }
            }
        }
    }

    private fun bindEditText(){
        binding.editTextNickname.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun goToCertifyEmailFragment(email : String){
        val action = SignUpProfileFragmentDirections.actionSignupProfileToCertifyEmail(email = email)
        findNavController().navigate(action)
    }
}