package com.example.domain.usecase.sign

import com.example.domain.base.UseCase
import com.example.domain.model.vo.LoginVo
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<LoginVo, Boolean>() {
    override suspend operator fun invoke(request: LoginVo): Boolean{
        return signRepository.login(request)
    }
}