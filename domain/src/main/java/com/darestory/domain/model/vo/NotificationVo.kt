package com.darestory.domain.model.vo

data class NotificationVo(
    var to: String? = "",
    var data: Data? = Data()
) {
    data class Data(
        var body: String? = "",
        var title: String? = ""
    )
}