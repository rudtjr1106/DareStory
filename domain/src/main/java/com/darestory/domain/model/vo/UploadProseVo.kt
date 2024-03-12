package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.WriteType

data class UploadProseVo(
    val type : WriteType = WriteType.NEW,
    val proseVo: ProseVo = ProseVo()
)