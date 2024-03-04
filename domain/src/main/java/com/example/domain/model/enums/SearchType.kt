package com.example.domain.model.enums

enum class SearchType(val type : String){
    TITLE("제목"), CONTENT("내용"), TITLE_CONTENT("제목+내용");

    companion object {
        fun valuesOf(value: String): SearchType {
            return SearchType.values().find {
                it.type == value
            } ?: SearchType.TITLE
        }
    }
}