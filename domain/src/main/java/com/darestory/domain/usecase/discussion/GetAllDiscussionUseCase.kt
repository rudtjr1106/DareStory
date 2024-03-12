package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetAllDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Unit, List<DiscussionVo>>() {
    override suspend operator fun invoke(request: Unit): List<DiscussionVo> {
        return discussionRepository.getAllDiscussion()
    }
}