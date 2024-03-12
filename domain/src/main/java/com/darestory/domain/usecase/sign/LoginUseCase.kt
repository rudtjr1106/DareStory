package com.darestory.domain.usecase.sign

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.LoginVo
import com.darestory.domain.repository.SignRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<LoginVo, Boolean>() {
    override suspend operator fun invoke(request: LoginVo): Boolean{
        return signRepository.login(request)
    }
}