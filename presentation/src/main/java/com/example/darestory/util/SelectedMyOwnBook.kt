package com.example.darestory.util

import com.example.domain.model.vo.MyBookVo

object SelectedMyOwnBook {

    var ownBook: MyBookVo = MyBookVo()
        private set

    fun updateInfo(ownBook: MyBookVo) {
        this.ownBook = ownBook
    }
}