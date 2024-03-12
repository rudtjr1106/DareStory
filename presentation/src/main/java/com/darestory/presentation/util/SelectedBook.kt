package com.darestory.presentation.util

import com.darestory.domain.model.vo.BookVo

object SelectedBook {

    var book: BookVo = BookVo()
        private set

    fun updateInfo(book: BookVo) {
        this.book = book
    }
}