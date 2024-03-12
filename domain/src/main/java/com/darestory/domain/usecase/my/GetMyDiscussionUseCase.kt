package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetMyDiscussionUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<DiscussionVo>>() {
    override suspend operator fun invoke(request: Unit): List<DiscussionVo> {
        return myRepository.getMyDiscussion()
    }
}