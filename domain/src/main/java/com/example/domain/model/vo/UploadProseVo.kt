package com.example.domain.model.vo

import com.example.domain.model.enums.WriteType

data class UploadProseVo(
    val type : WriteType = WriteType.NEW,
    val proseVo: ProseVo = ProseVo()
)