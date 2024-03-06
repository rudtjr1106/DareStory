package com.example.domain.model.enums

enum class CommentType(val type:Int) {
    NORMAL(0), REPLY(1);

    companion object {
        fun valueOf(value: Int): CommentType {
            return CommentType.values().find {
                it.type == value
            } ?: CommentType.NORMAL
        }
    }
}