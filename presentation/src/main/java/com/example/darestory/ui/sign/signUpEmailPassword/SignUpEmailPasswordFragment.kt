package com.example.darestory.ui.sign.signUpEmailPassword

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentSignupEmailPasswordBinding
import com.example.darestory.ui.common.spinner.SpinnerDialog
import com.example.darestory.ui.sign.verifyEmail.VerifyEmailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpEmailPasswordFragment : BaseFragment<FragmentSignupEmailPasswordBinding, SignUpEmailPasswordPageState, SignUpEmailPasswordViewModel>(
    FragmentSignupEmailPasswordBinding::inflate
), SpinnerDialog.OnItemSelectedListener {

    @Inject
    lateinit var spinnerDialog: SpinnerDialog

    private lateinit var text : String
    private lateinit var spannableString: Spannable

    override val viewModel: SignUpEmailPasswordViewModel by viewModels()

    companion object{
        const val PASSWORD_TERMS = 8
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            bindEditText()
            clickAbleString()
            viewModel.getAllEmail()
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
            is SignUpEmailPasswordEvent.ShowEmailDuplicateErrorTextEvent -> showIconAndText()
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

    private fun bindEditText(){
        binding.editTextEmailIdInput.setOnEditorActionListener { _, actionId, _ ->
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

    override fun onItemSelected(itemName: String) {
        viewModel.onSelectedEmailDomain(itemName)
    }

    private fun clickAbleString(){
        text = getString(R.string.sign_up_about_service_personal_info)
        val serviceText = getString(R.string.word_app_policy)
        spannableString = SpannableStringBuilder(text)
        val startIndex = text.indexOf(serviceText)
        val endIndex = text.indexOf(serviceText) + serviceText.length
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                goToServiceNotionPage()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(requireContext(), R.color.white)
            }
        }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val privateText = getString(R.string.word_private_policy)
        val startIndex2 = text.indexOf(privateText)
        val endIndex2 = text.indexOf(privateText) + privateText.length
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                goToPrivateNotionPage()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(requireContext(), R.color.white)
            }
        }, startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textServicePersonalApp.text = spannableString
        binding.textServicePersonalApp.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun showIconAndText(){
        binding.apply {
            imageErrorEmail.visibility = View.VISIBLE
            textErrorEmail.visibility = View.VISIBLE
        }
    }

    private fun goToPrivateNotionPage(){
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.private_policy)))
        )
    }

    private fun goToServiceNotionPage(){
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.service_policy)))
        )
    }
}