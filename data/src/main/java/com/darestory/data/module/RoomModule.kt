package com.darestory.data.module

import android.content.Context
import androidx.room.Room
import com.darestory.data.RoomDB
import com.darestory.data.RoomDiscussionDatabase
import com.darestory.data.RoomProseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val PROSE_DB_NAME = "database-prose"
    private const val DISCUSSION_DB_NAME = "database-discussion"

    @Provides
    @Singleton
    @RoomDB
    fun provideRoomProseDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomProseDatabase::class.java, PROSE_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    @RoomDB
    fun provideRoomDiscussionDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomDiscussionDatabase::class.java, DISCUSSION_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
}