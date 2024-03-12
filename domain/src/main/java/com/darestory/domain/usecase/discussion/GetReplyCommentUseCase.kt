package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.CommentRequestVo
import com.darestory.domain.model.vo.DisCommentVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetReplyCommentUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<CommentRequestVo, DisCommentVo >() {
    override suspend operator fun invoke(request: CommentRequestVo): DisCommentVo {
        return discussionRepository.getDiscussionReplyComment(request)
    }
}