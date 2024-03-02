package com.example.darestory.ui.main.home.detail.write.prose

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentProseWriteBinding
import com.example.darestory.ui.common.CommonDialog
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
                    sortEvent(it as ProseWriteEvent)
                }
            }
        }
    }

    private fun sortEvent(event: ProseWriteEvent){
        when(event){
            ProseWriteEvent.OnClickBack -> findNavController().popBackStack()
            ProseWriteEvent.SuccessUpload -> showSuccessDialog()
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

    private fun bindEditText() {
        binding.apply {
            editTextTitleInput.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) imageLineEditTitle.setBackgroundResource(R.drawable.img_line_purple_dark_600)
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