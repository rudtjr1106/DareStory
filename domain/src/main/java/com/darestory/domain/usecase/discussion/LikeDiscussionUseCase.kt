package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.LikeVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class LikeDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<LikeVo, Boolean>() {
    override suspend operator fun invoke(request: LikeVo): Boolean {
        return if(request.isLiked) discussionRepository.likeCancel(request) else discussionRepository.likeAdd(request)
    }
}