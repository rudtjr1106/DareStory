package com.example.domain.usecase

import com.example.domain.base.UseCase
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, Boolean>() {
    override suspend operator fun invoke(request: Unit): Boolean{
        return signRepository.sendEmailVerification()
    }
}