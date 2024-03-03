package com.example.data.module

import android.content.Context
import androidx.room.Room
import com.example.data.RoomDB
import com.example.data.RoomProseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DB_NAME = "database-prose"

    @Provides
    @Singleton
    @RoomDB
    fun provideRoomDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomProseDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
}