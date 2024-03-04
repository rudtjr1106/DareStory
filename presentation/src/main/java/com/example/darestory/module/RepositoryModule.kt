package com.example.darestory.module

import com.example.data.repository.DiscussionRepositoryImpl
import com.example.data.repository.ProseRepositoryImpl
import com.example.data.repository.ReportRepositoryImpl
import com.example.data.repository.SignRepositoryImpl
import com.example.domain.repository.DiscussionRepository
import com.example.domain.repository.ProseRepository
import com.example.domain.repository.ReportRepository
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

    @Singleton
    @Binds
    abstract fun provideReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository

    @Singleton
    @Binds
    abstract fun provideDiscussionRepository(discussionRepositoryImpl: DiscussionRepositoryImpl): DiscussionRepository
}