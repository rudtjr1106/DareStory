package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.NoticeVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<NoticeVo>>() {
    override suspend operator fun invoke(request: Unit): List<NoticeVo> {
        return myRepository.getNoticeList()
    }
}