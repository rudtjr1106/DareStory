package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class DeleteProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<Int, Boolean>() {
    override suspend operator fun invoke(request: Int): Boolean {
        return proseRepository.deleteProse(request)
    }
}