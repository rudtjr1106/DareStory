package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class InsertRecentDiscussionSearchUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<String, Boolean>() {
    override suspend operator fun invoke(request: String): Boolean {
        return discussionRepository.insertRecentSearch(request)
    }
}