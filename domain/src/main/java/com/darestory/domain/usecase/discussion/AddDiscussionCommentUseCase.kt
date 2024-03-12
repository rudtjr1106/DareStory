package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.UpdateCommentVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class AddDiscussionCommentUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<UpdateCommentVo, Boolean>() {
    override suspend operator fun invoke(request: UpdateCommentVo): Boolean {
        return discussionRepository.addComment(request)
    }
}