package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.RecentSearchVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class InsertRecentProseSearchUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<RecentSearchVo, Boolean>() {
    override suspend operator fun invoke(request: RecentSearchVo): Boolean {
        return proseRepository.insertRecentSearch(request)
    }
}