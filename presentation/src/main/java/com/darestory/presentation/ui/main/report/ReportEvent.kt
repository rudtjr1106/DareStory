package com.darestory.presentation.ui.main.report

import com.darestory.presentation.Event

sealed class ReportEvent : Event {
    object ShowReportTypeSpinnerEvent : ReportEvent()
    object GoBackEvent : ReportEvent()
    object SuccessReportEvent : ReportEvent()
}