package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.NoticeVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<NoticeVo>>() {
    override suspend operator fun invoke(request: Unit): List<NoticeVo> {
        return myRepository.getNoticeList()
    }
}