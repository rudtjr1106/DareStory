package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.DiscussionRepository
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class GetDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<Int, DiscussionVo>() {
    override suspend operator fun invoke(request: Int): DiscussionVo {
        return discussionRepository.getDiscussion(request)
    }
}