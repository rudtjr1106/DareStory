package com.example.darestory.module

import com.example.data.repository.ProseRepositoryImpl
import com.example.data.repository.SignRepositoryImpl
import com.example.domain.repository.ProseRepository
import com.example.domain.repository.SignRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideSignRepository(signRepositoryImpl: SignRepositoryImpl): SignRepository

    @Singleton
    @Binds
    abstract fun provideProseRepository(proseRepositoryImpl: ProseRepositoryImpl): ProseRepository
}