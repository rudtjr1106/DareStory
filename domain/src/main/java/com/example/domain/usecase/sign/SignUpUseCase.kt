package com.example.domain.usecase.sign

import com.example.domain.base.UseCase
import com.example.domain.model.vo.UserVo
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<UserVo, Boolean>() {
    override suspend operator fun invoke(request: UserVo): Boolean{
        return signRepository.signUp(request.email, request.password)
    }

    suspend fun addMyInfo(request: UserVo) : Boolean{
        return signRepository.addMyInfo(request)
    }
}