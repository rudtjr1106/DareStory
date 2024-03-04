package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetAllDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Unit, List<DiscussionVo>>() {
    override suspend operator fun invoke(request: Unit): List<DiscussionVo> {
        return discussionRepository.getAllDiscussion()
    }
}