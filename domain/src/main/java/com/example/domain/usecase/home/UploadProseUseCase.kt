package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.enums.WriteType
import com.example.domain.model.vo.UploadProseVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class UploadProseUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<UploadProseVo, Boolean>() {
    override suspend operator fun invoke(request: UploadProseVo): Boolean {
        return when(request.type){
            WriteType.EDIT -> proseRepository.update(request.proseVo)
            WriteType.NEW -> proseRepository.upload(request.proseVo)
        }
    }
}