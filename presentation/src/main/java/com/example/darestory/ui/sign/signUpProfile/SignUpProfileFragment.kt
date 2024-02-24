package com.example.darestory.ui.sign.signUpProfile

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentSignupProfileBinding
import com.example.darestory.ui.common.spinner.SpinnerDialog
import com.example.darestory.ui.sign.signUpEmailPassword.SignUpEmailPasswordEvent
import com.example.darestory.util.DareLog
import com.example.domain.model.error.NickNameError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
            is SignUpProfileEvent.GoCertifyEmail -> goToCertifyEmailFragment()
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

    private fun goToCertifyEmailFragment(){
        val action = SignUpProfileFragmentDirections.actionSignupProfileToCertifyEmail()
        findNavController().navigate(action)
    }
}