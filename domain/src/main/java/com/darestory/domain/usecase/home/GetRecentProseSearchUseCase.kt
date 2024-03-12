package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.repository.ProseRepository
import javax.inject.Inject

class GetRecentProseSearchUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<Unit, List<String>>() {
    override suspend operator fun invoke(request: Unit): List<String> {
        return proseRepository.getRecentSearch()
    }
}