package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entitiy.EntityTable
import com.example.data.entitiy.RecentSearchProseEntity
import com.example.domain.model.vo.RecentSearchVo

@Dao
interface RecentSearchProseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProse(recentSearchProse: RecentSearchProseEntity)

    @Query("SELECT * FROM ${EntityTable.RECENT_SEARCH_PROSE} ORDER BY saveTime DESC LIMIT :limit")
    suspend fun getRecentSearches(limit: Int): List<RecentSearchProseEntity>
}