package com.example.darestory.module

import com.example.domain.repository.SignRepository
import com.example.domain.usecase.sign.CheckEmailVerifiedUseCase
import com.example.domain.usecase.sign.GetAllEmailUseCase
import com.example.domain.usecase.sign.GetAllNickNameUseCase
import com.example.domain.usecase.sign.LoginUseCase
import com.example.domain.usecase.sign.SendEmailVerificationUseCase
import com.example.domain.usecase.sign.SendPasswordResetUseCase
import com.example.domain.usecase.sign.SignUpUseCase
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