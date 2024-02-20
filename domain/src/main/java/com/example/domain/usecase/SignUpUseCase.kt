package com.example.domain.usecase

import com.example.domain.base.UseCase
import com.example.domain.model.SignUpVo
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<SignUpVo, Boolean>() {
    override suspend operator fun invoke(request: SignUpVo): Boolean{
        return signRepository.signUp(request)
    }
}