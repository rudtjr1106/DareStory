package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.NoticeVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetNoticeDetailUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Int, NoticeVo>() {
    override suspend operator fun invoke(request: Int): NoticeVo {
        return myRepository.getNoticeDetail(request)
    }
}