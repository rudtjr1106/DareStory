package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.RecentSearchProseDao
import com.example.data.entitiy.RecentSearchProseEntity

@Database(entities = [RecentSearchProseEntity::class], version = 2, exportSchema = false)
abstract class RoomProseDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchProseDao
}