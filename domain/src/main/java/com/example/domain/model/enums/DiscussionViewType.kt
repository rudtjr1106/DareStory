package com.example.domain.model.enums

enum class DiscussionViewType(val type:Int) {
    TOP(0), DISCUSSION(1);

    companion object {
        fun valueOf(value: Int): DiscussionViewType {
            return DiscussionViewType.values().find {
                it.type == value
            } ?: DiscussionViewType.TOP
        }
    }
}