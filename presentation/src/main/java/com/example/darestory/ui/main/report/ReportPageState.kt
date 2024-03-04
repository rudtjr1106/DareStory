package com.example.darestory.ui.main.report

import com.example.darestory.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ReportPageState(
    var reportType : MutableStateFlow<String>,
    val reportTypeIsEmpty : StateFlow<Boolean>,
    var reportContent : MutableStateFlow<String>,
    val reportContentIsEmpty : StateFlow<Boolean>,
    val isReportBtnEnable : StateFlow<Boolean>
) : PageState