package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.enums.WriteType
import com.darestory.domain.model.vo.UploadProseVo
import com.darestory.domain.repository.ProseRepository
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