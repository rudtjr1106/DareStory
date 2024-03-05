package com.example.domain.model.enums

import com.example.domain.model.vo.ProseVo

data class UploadProseVo(
    val type : WriteType = WriteType.NEW,
    val proseVo: ProseVo = ProseVo()
)