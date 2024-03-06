package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.model.vo.CommentRequestVo
import com.example.domain.model.vo.DisCommentVo
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetReplyCommentUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<CommentRequestVo, DisCommentVo >() {
    override suspend operator fun invoke(request: CommentRequestVo): DisCommentVo {
        return discussionRepository.getDiscussionReplyComment(request)
    }
}