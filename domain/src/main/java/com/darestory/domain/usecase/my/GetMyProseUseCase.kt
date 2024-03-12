package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetMyProseUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<ProseVo>>() {
    override suspend operator fun invoke(request: Unit): List<ProseVo> {
        return myRepository.getMyProse()
    }
}