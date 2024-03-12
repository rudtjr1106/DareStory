package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class DeleteDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Int, Boolean>() {
    override suspend operator fun invoke(request: Int): Boolean {
        return discussionRepository.deleteDiscussion(request)
    }
}