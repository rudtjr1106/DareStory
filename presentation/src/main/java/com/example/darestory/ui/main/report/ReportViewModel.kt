package com.example.darestory.ui.main.report

import androidx.lifecycle.viewModelScope
import com.example.darestory.PageState
import com.example.darestory.base.BaseViewModel
import com.example.darestory.util.TimeFormatter
import com.example.darestory.util.UserInfo
import com.example.domain.model.vo.ReportVo
import com.example.domain.usecase.report.ReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportUseCase: ReportUseCase
) : BaseViewModel<ReportPageState>() {

    private val reportTypeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val reportTypeIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val reportContentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val reportContentIsEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val isReportBtnEnableEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: ReportPageState = ReportPageState(
        reportTypeStateFlow,
        reportTypeIsEmptyStateFlow.asStateFlow(),
        reportContentStateFlow,
        reportContentIsEmptyStateFlow.asStateFlow(),
        isReportBtnEnableEmptyStateFlow.asStateFlow()
    )

    private lateinit var userName : String

    fun setUserName(name : String){
        userName = name
    }

    fun onChangedReportTypeTextAfter(){
        updateReportTypeIsEmpty(reportTypeStateFlow.value.isEmpty())
        updateIsReportBtnEnable(!reportTypeIsEmptyStateFlow.value && !reportContentIsEmptyStateFlow.value)
    }

    fun onChangedReportContentTextAfter(){
        updateReportContentIsEmpty(reportContentStateFlow.value.isEmpty())
        updateIsReportBtnEnable(!reportTypeIsEmptyStateFlow.value && !reportContentIsEmptyStateFlow.value)
    }

    private fun updateReportTypeIsEmpty(isEmpty : Boolean){
        viewModelScope.launch {
            reportTypeIsEmptyStateFlow.update { isEmpty }
        }
    }

    private fun updateReportContentIsEmpty(isEmpty : Boolean){
        viewModelScope.launch {
            reportContentIsEmptyStateFlow.update { isEmpty }
        }
    }


    fun onClickSpinner(){
        emitEventFlow(ReportEvent.ShowReportTypeSpinnerEvent)
    }

    fun onClickReportBtn(){
        val request = ReportVo(
            userName = userName,
            reportType = reportTypeStateFlow.value,
            reportContent = reportContentStateFlow.value,
            reportedAt = TimeFormatter.getNowDateAndTime(),
            reportedUser = UserInfo.info.nickName
        )
        viewModelScope.launch {
            val result = reportUseCase(request)
            if(result) successReport()
        }
    }

    private fun successReport(){
        emitEventFlow(ReportEvent.SuccessReportEvent)
    }

    fun updateReportType(itemName : String){
        viewModelScope.launch {
            reportTypeStateFlow.update { itemName }
            onChangedReportTypeTextAfter()
        }
    }

    fun onClickBackBtn(){
        emitEventFlow(ReportEvent.GoBackEvent)
    }

    private fun updateIsReportBtnEnable(enable : Boolean){
        viewModelScope.launch {
            isReportBtnEnableEmptyStateFlow.update { enable }
        }
    }
}