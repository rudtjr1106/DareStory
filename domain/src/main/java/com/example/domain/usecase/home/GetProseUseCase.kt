package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class GetProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<Int, ProseVo>() {
    override suspend operator fun invoke(request: Int): ProseVo{
        return proseRepository.getProse(request)
    }
}