package com.darestory.data.module

import com.darestory.data.RoomDB
import com.darestory.data.RoomDiscussionDatabase
import com.darestory.data.RoomProseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideRecentProseSearchDao(@RoomDB db: RoomProseDatabase) = db.recentSearchDao()

    @Singleton
    @Provides
    fun provideRecentDiscussionSearchDao(@RoomDB db: RoomDiscussionDatabase) = db.recentSearchDao()
}