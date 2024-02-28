package com.example.domain.usecase.sign

import com.example.domain.base.UseCase
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class GetAllNickNameUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, List<String>>() {
    override suspend operator fun invoke(request: Unit): List<String>{
        return signRepository.getAllNickName()
    }
}