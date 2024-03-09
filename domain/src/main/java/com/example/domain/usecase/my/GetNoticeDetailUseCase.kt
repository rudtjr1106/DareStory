package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.NoticeVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetNoticeDetailUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Int, NoticeVo>() {
    override suspend operator fun invoke(request: Int): NoticeVo {
        return myRepository.getNoticeDetail(request)
    }
}