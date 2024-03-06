package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.model.vo.UpdateReplyCommentVo
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class DeleteDiscussionReplyCommentUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<UpdateReplyCommentVo, Boolean>() {
    override suspend operator fun invoke(request: UpdateReplyCommentVo): Boolean {
        return discussionRepository.deleteReplyComment(request)
    }
}