package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.LikeVo
import com.darestory.domain.repository.ProseRepository
import javax.inject.Inject

class LikeProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<LikeVo, Boolean>() {
    override suspend operator fun invoke(request: LikeVo): Boolean {
        return if(request.isLiked) proseRepository.likeCancel(request) else proseRepository.likeAdd(request)
    }
}