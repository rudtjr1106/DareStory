package com.example.darestory.ui.sign.verifyEmail

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentVerifyEmailBinding
import com.example.darestory.ui.main.MainActivity
import com.example.darestory.util.DareToast
import com.example.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyEmailFragment : BaseFragment<FragmentVerifyEmailBinding, PageState.Default, VerifyEmailViewModel>(
    FragmentVerifyEmailBinding::inflate
) {

    companion object{
        const val RESEND_EMAIL = "이곳"
    }


    override val viewModel: VerifyEmailViewModel by viewModels()
    private val verifyEmailFragmentArgs : VerifyEmailFragmentArgs by navArgs()
    private lateinit var text : String
    private lateinit var spannableString: Spannable

    override fun initView() {
        binding.apply {
            vm = viewModel
            clickAbleString()
            viewModel.sendEmailVerification()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as VerifyEmailEvent)
                }
            }
        }
    }

    private fun clickAbleString(){
        text = getString(R.string.sign_up_verify_email, verifyEmailFragmentArgs.email)
        spannableString = SpannableStringBuilder(text)
        val startIndex = text.indexOf(verifyEmailFragmentArgs.email)
        val endIndex = text.indexOf(verifyEmailFragmentArgs.email) + verifyEmailFragmentArgs.email.length
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                viewModel.onClickEmail(verifyEmailFragmentArgs.email)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(requireContext(), R.color.dark_purple_600)
            }
        }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val startIndex2 = text.indexOf(RESEND_EMAIL)
        val endIndex2 = text.indexOf(RESEND_EMAIL) + RESEND_EMAIL.length
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                viewModel.onClickResendText()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(requireContext(), R.color.dark_purple_600)
            }
        }, startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textVerifyExplain.text = spannableString
        binding.textVerifyExplain.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun sortEvent(event : VerifyEmailEvent){
        when(event){
            is VerifyEmailEvent.GoToUrl -> goToDomainUrl(event.url)
            is VerifyEmailEvent.ErrorVerify -> showErrorToast()
            is VerifyEmailEvent.GoToMain -> goToMain()
        }
    }

    private fun goToDomainUrl(url : String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun showErrorToast(){
        context?.let {
            DareToast.createToast(ToastType.ERROR,
                it, R.string.sign_up_email_verify_error_toast).show()
        }
    }

    private fun goToMain(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onStart() {
        super.onStart()
    }
}