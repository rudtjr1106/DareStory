package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.BookVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetMyReadBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<BookVo>>() {
    override suspend operator fun invoke(request: Unit): List<BookVo> {
        return myRepository.getMyReadBook()
    }
}