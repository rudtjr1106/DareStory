package com.darestory.domain.model.enums

enum class ReadOrOwnType(val type : Int) {
    READ_BOOK(0), OWN_BOOK(1), SELECT_OWN_BOOK(2);

    companion object {
        fun valueOf(value: Int): ReadOrOwnType {
            return ReadOrOwnType.values().find {
                it.type == value
            } ?: ReadOrOwnType.READ_BOOK
        }
    }
}