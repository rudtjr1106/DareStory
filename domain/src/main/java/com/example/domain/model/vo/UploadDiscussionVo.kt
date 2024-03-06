package com.example.domain.model.vo

import com.example.domain.model.enums.WriteType

data class UploadDiscussionVo(
    val type : WriteType = WriteType.NEW,
    val discussionVo: DiscussionVo = DiscussionVo()
)