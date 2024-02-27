package com.example.domain.model.enums

enum class HomeViewType(val type:Int) {
    TODAY_PROSE(0), ALL_PROSE(1);

    companion object {
        fun valueOf(value: Int): HomeViewType {
            return HomeViewType.values().find {
                it.type == value
            } ?: HomeViewType.TODAY_PROSE
        }
    }
}