package com.example.domain.usecase

import com.example.domain.base.UseCase
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class SendPasswordResetUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<String, Boolean>() {
    override suspend operator fun invoke(request: String): Boolean{
        return signRepository.sendPasswordResetEmail(request)
    }
}