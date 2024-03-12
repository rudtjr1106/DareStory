package com.darestory.domain.usecase.sign

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.AppInfoResponseVo
import com.darestory.domain.repository.SignRepository
import javax.inject.Inject

class CheckAppInfoUseCase @Inject constructor(
    private val signRepository: SignRepository
) : UseCase<Unit, AppInfoResponseVo>() {
    override suspend operator fun invoke(request: Unit): AppInfoResponseVo {
        return signRepository.checkAppInfo()
    }
}