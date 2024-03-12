package com.darestory.presentation.ui.main.my.readOrOwnBook.write

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentMyOwnBookWriteBinding
import com.darestory.presentation.ui.common.CommonDialog
import com.darestory.presentation.util.DareToast
import com.darestory.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyOwnBookWriteFragment : BaseFragment<FragmentMyOwnBookWriteBinding, MyOwnBookWritePageState, MyOwnBookWriteViewModel>(
    FragmentMyOwnBookWriteBinding::inflate
) {

    @Inject
    lateinit var commonDialog : CommonDialog

    override val viewModel: MyOwnBookWriteViewModel by viewModels()

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
                    sortEvent(it as MyOwnBookWriteEvent)
                }
            }
        }
    }

    private fun sortEvent(event: MyOwnBookWriteEvent){
        when(event){
            MyOwnBookWriteEvent.OnClickBackEvent -> findNavController().popBackStack()
            MyOwnBookWriteEvent.ToastEmptyContentEvent -> showContentErrorToast()
            MyOwnBookWriteEvent.ToastEmptyTitleEvent -> showTitleErrorToast()
            MyOwnBookWriteEvent.ErrorUploadEvent -> showUploadErrorToast()
        }
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
        }
    }
    override fun onStart() {
        super.onStart()
    }
}