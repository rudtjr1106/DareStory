package com.darestory.presentation.ui.main.discussion.write

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentDiscussionWriteBinding
import com.darestory.presentation.ui.common.CommonDialog
import com.darestory.presentation.util.DareToast
import com.darestory.presentation.util.SelectedBook
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.enums.ToastType
import com.darestory.domain.model.vo.BookVo
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
            DiscussionWriteEvent.GoToBookSearch -> goToResultSearch()
            DiscussionWriteEvent.SuccessGetDiscussionEvent -> onResume()
            DiscussionWriteEvent.DeleteDiscussionEvent -> showDeleteDiscussionErrorDialog()
            DiscussionWriteEvent.ErrorUploadEvent -> showUploadErrorToast()
        }
    }

    private fun showDeleteDiscussionErrorDialog(){
        commonDialog
            .setTitle(R.string.dialog_delete_discussion_error_title)
            .setDescription(R.string.dialog_delete_prose_error_content)
            .setPositiveButton(R.string.word_confirm){
                findNavController().popBackStack()
                commonDialog.dismiss()
            }
            .showOnlyPositive()
            .show()
    }

    private fun showUploadErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_upload_fail).show()
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

    private fun goToResultSearch(){
        val action =
            DiscussionWriteFragmentDirections.actionDiscussionWriteToResultSearch(detailType = DetailType.BOOK)
        findNavController().navigate(action)
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

    override fun onDestroyView() {
        SelectedBook.updateInfo(BookVo())
        super.onDestroyView()
    }
    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        if(SelectedBook.book.title.isNotEmpty()){
            setView()
        }
    }

    private fun setView(){
        binding.apply {
            constraintLayoutSearchBook.visibility = View.GONE
            constraintLayoutSearchedBook.visibility = View.VISIBLE
            textBookTitle.text = SelectedBook.book.title
            textBookSearch.text = getString(R.string.discussion_book_searched)
            textBookAuthor.text = getString(R.string.book_author, SelectedBook.book.author)
        }
    }
}