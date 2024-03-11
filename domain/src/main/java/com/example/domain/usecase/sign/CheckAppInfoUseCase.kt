package com.example.domain.usecase.sign

import com.example.domain.base.UseCase
import com.example.domain.model.vo.AppInfoResponseVo
import com.example.domain.repository.SignRepository
import javax.inject.Inject

class CheckAppInfoUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, AppInfoResponseVo>() {
    override suspend operator fun invoke(request: Unit): AppInfoResponseVo {
        return signRepository.checkAppInfo()
    }
}