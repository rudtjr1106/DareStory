package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.repository.ProseRepository
import javax.inject.Inject

class GetProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<Int, ProseVo>() {
    override suspend operator fun invoke(request: Int): ProseVo{
        return proseRepository.getProse(request)
    }
}