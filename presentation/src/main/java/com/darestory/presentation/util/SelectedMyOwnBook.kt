package com.darestory.presentation.util

import com.darestory.domain.model.vo.MyBookVo

object SelectedMyOwnBook {

    var ownBook: MyBookVo = MyBookVo()
        private set

    fun updateInfo(ownBook: MyBookVo) {
        this.ownBook = ownBook
    }
}