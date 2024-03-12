package com.darestory.domain.usecase.sign

import com.darestory.domain.base.UseCase
import com.darestory.domain.repository.SignRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, Boolean>() {
    override suspend operator fun invoke(request: Unit): Boolean{
        return signRepository.logout()
    }
}