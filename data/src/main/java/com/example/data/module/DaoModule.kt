package com.example.data.module

import com.example.data.RoomDB
import com.example.data.RoomDiscussionDatabase
import com.example.data.RoomProseDatabase
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