package com.darestory.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darestory.data.dao.RecentSearchProseDao
import com.darestory.data.entitiy.RecentSearchProseEntity

@Database(entities = [RecentSearchProseEntity::class], version = 1, exportSchema = false)
abstract class RoomProseDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchProseDao
}