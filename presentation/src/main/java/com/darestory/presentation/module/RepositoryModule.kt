package com.darestory.presentation.module

import com.darestory.data.repository.BookRepositoryImpl
import com.darestory.data.repository.DiscussionRepositoryImpl
import com.darestory.data.repository.MyRepositoryImpl
import com.darestory.data.repository.ProseRepositoryImpl
import com.darestory.data.repository.ReportRepositoryImpl
import com.darestory.data.repository.SignRepositoryImpl
import com.darestory.domain.repository.BookRepository
import com.darestory.domain.repository.DiscussionRepository
import com.darestory.domain.repository.MyRepository
import com.darestory.domain.repository.ProseRepository
import com.darestory.domain.repository.ReportRepository
import com.darestory.domain.repository.SignRepository
import dagger.Binds
import dagger.Module
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

    @Singleton
    @Binds
    abstract fun provideBookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

    @Singleton
    @Binds
    abstract fun provideMyRepository(myRepositoryImpl: MyRepositoryImpl): MyRepository
}