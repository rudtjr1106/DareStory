package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.repository.ProseRepository
import javax.inject.Inject

class InsertRecentProseSearchUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<String, Boolean>() {
    override suspend operator fun invoke(request: String): Boolean {
        return proseRepository.insertRecentSearch(request)
    }
}