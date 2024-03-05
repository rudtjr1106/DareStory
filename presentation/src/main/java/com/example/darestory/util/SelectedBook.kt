package com.example.darestory.util

import com.example.domain.model.vo.BookVo

object SelectedBook {

    var book: BookVo = BookVo()
        private set

    fun updateInfo(book: BookVo) {
        this.book = book
    }
}