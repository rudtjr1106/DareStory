package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entitiy.EntityTable
import com.example.data.entitiy.RecentSearchDiscussionEntity

@Dao
interface RecentSearchDiscussionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProse(recentSearchDiscussion: RecentSearchDiscussionEntity)

    @Query("SELECT * FROM ${EntityTable.RECENT_SEARCH_DISCUSSION} ORDER BY saveTime DESC LIMIT :limit")
    suspend fun getRecentSearches(limit: Int): List<RecentSearchDiscussionEntity>
}