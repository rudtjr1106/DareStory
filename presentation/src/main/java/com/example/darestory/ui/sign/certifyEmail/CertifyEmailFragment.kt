package com.example.darestory.ui.sign.certifyEmail

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentCertifyEmailBinding
import com.example.darestory.ui.sign.signUpProfile.SignUpProfileFragmentArgs
import com.example.darestory.util.DareLog
import com.example.darestory.util.DareToast
import com.example.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CertifyEmailFragment : BaseFragment<FragmentCertifyEmailBinding, PageState.Default, CertifyEmailViewModel>(
    FragmentCertifyEmailBinding::inflate
) {

    companion object{
        const val RESEND_EMAIL = "이곳"
    }


    override val viewModel: CertifyEmailViewModel by viewModels()
    private val certifyEmailFragmentArgs : CertifyEmailFragmentArgs by navArgs()

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
                    sortEvent(it as CertifyEmailEvent)
                }
            }
        }
    }

    private fun clickAbleString(){
        val text = getString(R.string.sign_up_certify_email, certifyEmailFragmentArgs.email)
        val spannableString = SpannableStringBuilder(text)
        val startIndex = text.indexOf(certifyEmailFragmentArgs.email)
        val endIndex = text.indexOf(certifyEmailFragmentArgs.email) + certifyEmailFragmentArgs.email.length
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                viewModel.onClickEmail(certifyEmailFragmentArgs.email)
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
        binding.textCertifyExplain.text = spannableString
        binding.textCertifyExplain.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun sortEvent(event : CertifyEmailEvent){
        when(event){
            is CertifyEmailEvent.GoToUrl -> goToDomainUrl(event.url)
            is CertifyEmailEvent.ErrorCertify -> showErrorToast()
            is CertifyEmailEvent.GoToMain -> TODO()
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

    override fun onStart() {
        super.onStart()
    }
}