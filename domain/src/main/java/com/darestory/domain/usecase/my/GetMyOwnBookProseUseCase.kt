package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetMyOwnBookProseUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<String, List<ProseVo>>() {
    override suspend operator fun invoke(request: String): List<ProseVo> {
        return myRepository.getMyOwnBookProse(request)
    }
}