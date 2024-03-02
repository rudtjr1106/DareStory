package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.enums.ProseWriteType
import com.example.domain.model.enums.UploadProseVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class UploadProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<UploadProseVo, Boolean>() {
    override suspend operator fun invoke(request: UploadProseVo): Boolean {
        return when(request.type){
            ProseWriteType.EDIT -> proseRepository.update(request.proseVo)
            ProseWriteType.NEW -> proseRepository.upload(request.proseVo)
        }
    }
}