package com.example.domain.model.enums

enum class ReadOrOwnType(val type : Int) {
    READ_BOOK(0), OWN_BOOK(1);

    companion object {
        fun valueOf(value: Int): ReadOrOwnType {
            return ReadOrOwnType.values().find {
                it.type == value
            } ?: ReadOrOwnType.READ_BOOK
        }
    }
}