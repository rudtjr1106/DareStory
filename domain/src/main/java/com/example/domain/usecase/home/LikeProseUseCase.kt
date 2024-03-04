package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.LikeVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class LikeProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<LikeVo, Boolean>() {
    override suspend operator fun invoke(request: LikeVo): Boolean {
        return if(request.isLiked) proseRepository.likeCancel(request) else proseRepository.likeAdd(request)
    }
}