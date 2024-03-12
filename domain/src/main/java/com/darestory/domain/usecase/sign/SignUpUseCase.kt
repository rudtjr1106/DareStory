package com.darestory.domain.usecase.sign

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.LoginVo
import com.darestory.domain.model.vo.UserVo
import com.darestory.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<LoginVo, Boolean>() {
    override suspend operator fun invoke(request: LoginVo): Boolean{
        return signRepository.signUp(request.email, request.password)
    }

    suspend fun addMyInfo(request: UserVo) : Boolean{
        return signRepository.addMyInfo(request)
    }
}