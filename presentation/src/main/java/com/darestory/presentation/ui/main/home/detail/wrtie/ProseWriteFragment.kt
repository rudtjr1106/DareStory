package com.darestory.presentation.ui.main.home.detail.wrtie

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentProseWriteBinding
import com.darestory.presentation.ui.common.CommonDialog
import com.darestory.presentation.util.DareToast
import com.darestory.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProseWriteFragment : BaseFragment<FragmentProseWriteBinding, ProseWritePageState, ProseWriteViewModel>(
    FragmentProseWriteBinding::inflate
) {

    @Inject
    lateinit var commonDialog : CommonDialog

    override val viewModel: ProseWriteViewModel by viewModels()
    private val proseWriteFragmentArgs : ProseWriteFragmentArgs by navArgs()


    override fun initView() {
        binding.apply {
            vm = viewModel
            bindEditText()
            viewModel.loadPage(proseWriteFragmentArgs.proseId, proseWriteFragmentArgs.proseWriteType)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as ProseWriteEvent)
                }
            }
        }
    }

    private fun sortEvent(event: ProseWriteEvent){
        when(event){
            ProseWriteEvent.OnClickBackEvent -> findNavController().popBackStack()
            ProseWriteEvent.SuccessUploadEvent -> showSuccessDialog()
            ProseWriteEvent.ToastEmptyContentEvent -> showContentErrorToast()
            ProseWriteEvent.ToastEmptyTitleEvent -> showTitleErrorToast()
            ProseWriteEvent.ErrorUploadEvent -> showUploadErrorToast()
            ProseWriteEvent.FocusEditTextEvent -> focusEditTextAuthorSay()
            ProseWriteEvent.DeleteProseEvent -> showDeleteProseErrorDialog()
        }
    }

    private fun focusEditTextAuthorSay(){
        binding.editTextAuthorSayInput.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editTextAuthorSayInput, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun showDeleteProseErrorDialog(){
        commonDialog
            .setTitle(R.string.dialog_delete_prose_error_title)
            .setDescription(R.string.dialog_delete_prose_error_content)
            .setPositiveButton(R.string.word_confirm){
                findNavController().popBackStack()
                commonDialog.dismiss()
            }
            .showOnlyPositive()
            .show()
    }

    private fun showSuccessDialog(){
        commonDialog
            .showIcon()
            .setDescription(R.string.home_upload_complete)
            .setPositiveButton(R.string.word_confirm){
                commonDialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }

    private fun showContentErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_check_write_content).show()
    }

    private fun showTitleErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_check_write_title).show()
    }

    private fun showUploadErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_upload_fail).show()
    }

    private fun bindEditText() {
        binding.apply {
            editTextTitleInput.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) imageLineEditTitle.setBackgroundResource(R.drawable.img_line_dark_purple_600)
                else imageLineEditTitle.setBackgroundResource(R.drawable.img_line_gray_600)
            }

            editTextContentInput.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) editTextContentInput.setBackgroundResource(R.drawable.bg_rectangle_empty_dark_purple_600)
                else editTextContentInput.setBackgroundResource(R.drawable.bg_rectangle_empty_gray_600)
            }

            editTextAuthorSayInput.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    constraintLayoutAuthorSay.setBackgroundResource(R.drawable.bg_rectangle_empty_dark_purple_600)
                    textAuthorSay.setTextColor(ContextCompat.getColor(root.context, R.color.dark_purple_600))
                }
                else {
                    constraintLayoutAuthorSay.setBackgroundResource(R.drawable.bg_rectangle_empty_gray_600)
                    textAuthorSay.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
                }

            }
        }
    }
    override fun onStart() {
        super.onStart()
    }
}