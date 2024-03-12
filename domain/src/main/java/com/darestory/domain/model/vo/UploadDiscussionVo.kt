package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.WriteType

data class UploadDiscussionVo(
    val type : WriteType = WriteType.NEW,
    val discussionVo: DiscussionVo = DiscussionVo()
)