package com.darestory.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darestory.data.dao.RecentSearchDiscussionDao
import com.darestory.data.entitiy.RecentSearchDiscussionEntity

@Database(entities = [RecentSearchDiscussionEntity::class], version = 1, exportSchema = false)
abstract class RoomDiscussionDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDiscussionDao
}