package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetRecentDiscussionSearchUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Unit, List<String>>() {
    override suspend operator fun invoke(request: Unit): List<String> {
        return discussionRepository.getRecentSearch()
    }
}