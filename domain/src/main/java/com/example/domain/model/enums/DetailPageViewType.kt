package com.example.domain.model.enums

enum class DetailPageViewType(val type:Int) {
    CONTENT(0), PROSE_COMMENT(1), PROSE_AUTHOR_COMMENT(2);

    companion object {
        fun valueOf(value: Int): DetailPageViewType {
            return DetailPageViewType.values().find {
                it.type == value
            } ?: DetailPageViewType.CONTENT
        }
    }
}