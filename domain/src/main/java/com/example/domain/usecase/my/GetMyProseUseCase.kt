package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetMyProseUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<ProseVo>>() {
    override suspend operator fun invoke(request: Unit): List<ProseVo> {
        return myRepository.getMyProse()
    }
}