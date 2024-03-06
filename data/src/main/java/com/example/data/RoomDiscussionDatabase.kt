package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.RecentSearchDiscussionDao
import com.example.data.entitiy.RecentSearchDiscussionEntity

@Database(entities = [RecentSearchDiscussionEntity::class], version = 1, exportSchema = false)
abstract class RoomDiscussionDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDiscussionDao
}