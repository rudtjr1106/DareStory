package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.model.enums.WriteType
import com.example.domain.model.vo.UploadDiscussionVo
import com.example.domain.model.vo.UploadProseVo
import com.example.domain.repository.DiscussionRepository
import com.example.domain.repository.ProseRepository
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