package com.darestory.domain.usecase.sign

import com.darestory.domain.base.UseCase
import com.darestory.domain.repository.SignRepository
import javax.inject.Inject

class SendPasswordResetUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<String, Boolean>() {
    override suspend operator fun invoke(request: String): Boolean{
        return signRepository.sendPasswordResetEmail(request)
    }
}