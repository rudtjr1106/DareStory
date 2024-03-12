package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Int, DiscussionVo>() {
    override suspend operator fun invoke(request: Int): DiscussionVo {
        return discussionRepository.getDiscussion(request)
    }
}