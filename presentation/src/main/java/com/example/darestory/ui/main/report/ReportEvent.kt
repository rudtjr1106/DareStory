package com.example.darestory.ui.main.report

import com.example.darestory.Event

sealed class ReportEvent : Event {
    object ShowReportTypeSpinnerEvent : ReportEvent()
    object GoBackEvent : ReportEvent()
    object SuccessReportEvent : ReportEvent()
}