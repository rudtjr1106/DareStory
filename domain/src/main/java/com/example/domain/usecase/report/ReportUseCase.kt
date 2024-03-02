package com.example.domain.usecase.report

import com.example.domain.base.UseCase
import com.example.domain.model.vo.ReportVo
import com.example.domain.repository.ReportRepository
import javax.inject.Inject

class ReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) : UseCase<ReportVo, Boolean>() {
    override suspend operator fun invoke(request: ReportVo): Boolean {
        return reportRepository.report(request)
    }
}