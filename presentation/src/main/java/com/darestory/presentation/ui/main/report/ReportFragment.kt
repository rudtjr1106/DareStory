package com.darestory.presentation.ui.main.report

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentReportBinding
import com.darestory.presentation.ui.common.CommonDialog
import com.darestory.presentation.ui.common.spinner.SpinnerDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding, ReportPageState, ReportViewModel>(
    FragmentReportBinding::inflate
), SpinnerDialog.OnItemSelectedListener {

    @Inject
    lateinit var spinnerDialog: SpinnerDialog

    @Inject
    lateinit var commonDialog: CommonDialog

    override val viewModel: ReportViewModel by viewModels()
    private val reportFragmentArgs : ReportFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.setUserName(reportFragmentArgs.userName)
            textReportWho.text = getString(R.string.report_whos_name, reportFragmentArgs.userName)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as ReportEvent)
                }
            }
        }
    }

    private fun sortEvent(event : ReportEvent){
        when(event){
            ReportEvent.GoBackEvent -> findNavController().popBackStack()
            ReportEvent.ShowReportTypeSpinnerEvent -> onClickSpinner()
            ReportEvent.SuccessReportEvent -> showSuccessDialog()
        }
    }

    private fun onClickSpinner(){
        val items = resources.getStringArray(R.array.report_array)
        spinnerDialog.setItems(items.toList())
        spinnerDialog.setOnItemSelectedListener(this)
        spinnerDialog.show()
    }

    private fun showSuccessDialog(){
        commonDialog
            .showIcon()
            .setDescription(R.string.report_success)
            .setPositiveButton(R.string.word_confirm){
                commonDialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onItemSelected(itemName: String) {
        viewModel.updateReportType(itemName)
    }
}