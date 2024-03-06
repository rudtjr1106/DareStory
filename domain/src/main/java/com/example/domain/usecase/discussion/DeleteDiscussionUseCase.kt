package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class DeleteDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Int, Boolean>() {
    override suspend operator fun invoke(request: Int): Boolean {
        return discussionRepository.deleteDiscussion(request)
    }
}