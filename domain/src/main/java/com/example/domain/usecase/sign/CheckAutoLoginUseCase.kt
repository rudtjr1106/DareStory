package com.example.domain.usecase.sign

import com.example.domain.base.UseCase
import com.example.domain.model.vo.UserVo
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class CheckAutoLoginUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, UserVo>() {
    override suspend operator fun invoke(request: Unit): UserVo{
        return signRepository.checkAutoLogin()
    }
}