package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.repository.ProseRepository
import javax.inject.Inject

class GetAllProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<Unit, List<ProseVo>>() {
    override suspend operator fun invoke(request: Unit): List<ProseVo>{
        return proseRepository.getAllProse()
    }
}