package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetMyDiscussionUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<DiscussionVo>>() {
    override suspend operator fun invoke(request: Unit): List<DiscussionVo> {
        return myRepository.getMyDiscussion()
    }
}