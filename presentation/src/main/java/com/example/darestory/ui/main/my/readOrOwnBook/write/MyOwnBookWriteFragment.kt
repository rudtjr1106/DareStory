package com.example.darestory.ui.main.my.readOrOwnBook.write

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentMyOwnBookWriteBinding
import com.example.darestory.ui.common.CommonDialog
import com.example.darestory.util.DareToast
import com.example.domain.model.enums.ToastType
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
        }
    }
    private fun showContentErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_check_write_content).show()
    }

    private fun showTitleErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_check_write_title).show()
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