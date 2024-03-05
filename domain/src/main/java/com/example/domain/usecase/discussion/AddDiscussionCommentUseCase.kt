package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.model.vo.UpdateCommentVo
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class AddDiscussionCommentUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<UpdateCommentVo, Boolean>() {
    override suspend operator fun invoke(request: UpdateCommentVo): Boolean {
        return discussionRepository.addComment(request)
    }
}