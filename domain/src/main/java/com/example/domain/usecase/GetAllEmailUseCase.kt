package com.example.domain.usecase

import com.example.domain.base.UseCase
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class GetAllEmailUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, List<String>>() {
    override suspend operator fun invoke(request: Unit): List<String>{
        return signRepository.getAllEmail()
    }
}