package com.example.domain.model.enums

import com.example.domain.model.vo.ProseVo

data class UploadProseVo(
    val type : ProseWriteType = ProseWriteType.NEW,
    val proseVo: ProseVo = ProseVo()
)