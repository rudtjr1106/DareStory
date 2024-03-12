package com.darestory.domain.model.vo

data class NotificationVo(
    var to: String? = "",
    var notification: Notification? = Notification()
) {
    data class Notification(
        var body: String? = "",
        var title: String? = ""
    )
}