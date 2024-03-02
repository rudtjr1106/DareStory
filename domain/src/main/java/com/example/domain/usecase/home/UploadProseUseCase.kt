package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.AddCommentVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class UploadProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<ProseVo, Boolean>() {
    override suspend operator fun invoke(request: ProseVo): Boolean {
        return proseRepository.upload(request)
    }
}