package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.RecentSearchVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class GetRecentProseSearchUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<Unit, List<RecentSearchVo>>() {
    override suspend operator fun invoke(request: Unit): List<RecentSearchVo> {
        return proseRepository.getRecentSearch()
    }
}