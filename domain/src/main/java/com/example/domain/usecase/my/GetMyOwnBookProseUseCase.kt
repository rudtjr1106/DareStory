package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetMyOwnBookProseUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<String, List<ProseVo>>() {
    override suspend operator fun invoke(request: String): List<ProseVo> {
        return myRepository.getMyOwnBookProse(request)
    }
}