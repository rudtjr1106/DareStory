package com.darestory.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.RECENT_SEARCH_DISCUSSION)
data class RecentSearchDiscussionEntity(
    @PrimaryKey
    @ColumnInfo(name = "search") val search: String,
    @ColumnInfo(name = "saveTime") val saveTime: Long
)