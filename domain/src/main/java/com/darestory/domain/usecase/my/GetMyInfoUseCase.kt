package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.UserVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<String, UserVo>() {
    override suspend operator fun invoke(request: String): UserVo {
        return myRepository.getMyInfo(request)
    }
}