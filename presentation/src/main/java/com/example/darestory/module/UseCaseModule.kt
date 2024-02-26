package com.example.darestory.module

import com.example.domain.repository.SignRepository
import com.example.domain.usecase.CheckEmailVerifiedUseCase
import com.example.domain.usecase.GetAllEmailUseCase
import com.example.domain.usecase.GetAllNickNameUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.SendEmailVerificationUseCase
import com.example.domain.usecase.SendPasswordResetUseCase
import com.example.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: SignRepository) = SignUpUseCase(repository)

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: SignRepository) = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllNickNameUseCase(repository: SignRepository) = GetAllNickNameUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllEmailUseCase(repository: SignRepository) = GetAllEmailUseCase(repository)

    @Provides
    @Singleton
    fun provideCheckEmailVerifiedUseCase(repository: SignRepository) = CheckEmailVerifiedUseCase(repository)

    @Provides
    @Singleton
    fun provideSendEmailVerificationUseCase(repository: SignRepository) = SendEmailVerificationUseCase(repository)

    @Provides
    @Singleton
    fun provideSendPasswordResetUseCase(repository: SignRepository) = SendPasswordResetUseCase(repository)
}