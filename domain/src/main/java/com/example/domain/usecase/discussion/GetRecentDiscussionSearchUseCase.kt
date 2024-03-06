package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetRecentDiscussionSearchUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Unit, List<String>>() {
    override suspend operator fun invoke(request: Unit): List<String> {
        return discussionRepository.getRecentSearch()
    }
}