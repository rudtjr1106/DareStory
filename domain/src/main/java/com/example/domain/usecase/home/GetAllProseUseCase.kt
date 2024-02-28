package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.ProseRepository
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class GetAllProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<Unit, List<ProseVo>>() {
    override suspend operator fun invoke(request: Unit): List<ProseVo>{
        return proseRepository.getAllProse()
    }
}