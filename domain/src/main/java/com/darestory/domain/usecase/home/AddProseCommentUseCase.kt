package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.UpdateCommentVo
import com.darestory.domain.repository.ProseRepository
import javax.inject.Inject

class AddProseCommentUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<UpdateCommentVo, Boolean>() {
    override suspend operator fun invoke(request: UpdateCommentVo): Boolean {
        return proseRepository.addComment(request)
    }
}