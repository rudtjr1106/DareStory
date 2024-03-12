package com.darestory.domain.usecase.report

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.ReportVo
import com.darestory.domain.repository.ReportRepository
import javax.inject.Inject

class ReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) : UseCase<ReportVo, Boolean>() {
    override suspend operator fun invoke(request: ReportVo): Boolean {
        return reportRepository.report(request)
    }
}