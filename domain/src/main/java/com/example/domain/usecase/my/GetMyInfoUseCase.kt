package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.UserVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<String, UserVo>() {
    override suspend operator fun invoke(request: String): UserVo {
        return myRepository.getMyInfo(request)
    }
}