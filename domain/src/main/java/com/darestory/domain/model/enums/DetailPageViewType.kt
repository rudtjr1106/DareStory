package com.darestory.domain.model.enums

enum class DetailPageViewType(val type:Int) {
    CONTENT(0), PROSE_COMMENT(1), PROSE_AUTHOR_COMMENT(2), DISCUSSION_COMMENT(3), EMPTY(4);

    companion object {
        fun valueOf(value: Int): DetailPageViewType {
            return DetailPageViewType.values().find {
                it.type == value
            } ?: DetailPageViewType.CONTENT
        }
    }
}