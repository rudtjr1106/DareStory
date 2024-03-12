package com.darestory.domain.model.enums

enum class DetailType(val type : Int) {
    PROSE(0), DISCUSSION(1), BOOK(2);

    companion object {
        fun valueOf(value: Int): DetailType {
            return DetailType.values().find {
                it.type == value
            } ?: DetailType.PROSE
        }
    }
}