package com.darestory.domain.usecase.sign

import com.darestory.domain.base.UseCase
import com.darestory.domain.repository.SignRepository
import javax.inject.Inject

class GetAllNickNameUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, List<String>>() {
    override suspend operator fun invoke(request: Unit): List<String>{
        return signRepository.getAllNickName()
    }
}