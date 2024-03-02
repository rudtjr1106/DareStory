package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.AddCommentVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class AddProseCommentUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<AddCommentVo, Boolean>() {
    override suspend operator fun invoke(request: AddCommentVo): Boolean {
        return proseRepository.addComment(request)
    }
}