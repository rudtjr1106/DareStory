package com.darestory.domain.model.vo

import com.darestory.domain.model.enums.ReadOrOwnType

data class MyReadOrOwnBookVo(
    val myBookVo: MyBookVo = MyBookVo(),
    val bookVo: BookVo = BookVo(),
    val type: ReadOrOwnType = ReadOrOwnType.READ_BOOK
)