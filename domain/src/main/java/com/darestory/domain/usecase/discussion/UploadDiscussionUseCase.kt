package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.enums.WriteType
import com.darestory.domain.model.vo.UploadDiscussionVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class UploadDiscussionUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<UploadDiscussionVo, Boolean>() {
    override suspend operator fun invoke(request: UploadDiscussionVo): Boolean {
        return when(request.type){
            WriteType.EDIT -> discussionRepository.update(request.discussionVo)
            WriteType.NEW -> discussionRepository.upload(request.discussionVo)
        }
    }
}