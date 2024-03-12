package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.UpdateReplyCommentVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class AddDiscussionReplyCommentUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<UpdateReplyCommentVo, Boolean>() {
    override suspend operator fun invoke(request: UpdateReplyCommentVo): Boolean {
        return discussionRepository.addReplyComment(request)
    }
}