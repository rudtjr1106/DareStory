package com.example.domain.usecase.sign

import com.example.domain.base.UseCase
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class CheckEmailVerifiedUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, Boolean>() {
    override suspend operator fun invoke(request: Unit): Boolean{
        return signRepository.checkEmailVerified()
    }
}