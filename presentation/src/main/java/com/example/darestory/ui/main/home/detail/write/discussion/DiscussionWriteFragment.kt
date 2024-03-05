package com.example.darestory.ui.main.home.detail.write.discussion

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDiscussionWriteBinding
import com.example.darestory.ui.common.CommonDialog
import com.example.darestory.util.DareToast
import com.example.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DiscussionWriteFragment : BaseFragment<FragmentDiscussionWriteBinding, DiscussionWritePageState, DiscussionWriteViewModel>(
    FragmentDiscussionWriteBinding::inflate
) {

    @Inject
    lateinit var commonDialog : CommonDialog

    override val viewModel: DiscussionWriteViewModel by viewModels()
    private val discussionWriteFragmentArgs : DiscussionWriteFragmentArgs by navArgs()


    override fun initView() {
        binding.apply {
            vm = viewModel
            bindEditText()
            viewModel.test()
            viewModel.loadPage(discussionWriteFragmentArgs.discussionId, discussionWriteFragmentArgs.discussionWriteType)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as DiscussionWriteEvent)
                }
            }
        }
    }

    private fun sortEvent(event: DiscussionWriteEvent){
        when(event){
            DiscussionWriteEvent.OnClickBackEvent -> findNavController().popBackStack()
            DiscussionWriteEvent.SuccessUploadEvent -> showSuccessDialog()
            DiscussionWriteEvent.ToastEmptyContentEvent -> showContentErrorToast()
            DiscussionWriteEvent.ToastEmptyTitleEvent -> showTitleErrorToast()
            DiscussionWriteEvent.ToastEmptyBookEvent -> showBookErrorToast()
        }
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

    private fun showBookErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_check_book).show()
    }

    private fun bindEditText() {
        binding.apply {
            editTextTitleInput.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) imageLineEditTextTitleInput.setBackgroundResource(R.drawable.img_line_dark_purple_600)
                else imageLineEditTextTitleInput.setBackgroundResource(R.drawable.img_line_gray_600)
            }

            editTextContentInput.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) editTextContentInput.setBackgroundResource(R.drawable.bg_rectangle_empty_dark_purple_600)
                else editTextContentInput.setBackgroundResource(R.drawable.bg_rectangle_empty_gray_600)
            }
        }
    }
    override fun onStart() {
        super.onStart()
    }
}