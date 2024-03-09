package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.BookVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetMyReadBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<BookVo>>() {
    override suspend operator fun invoke(request: Unit): List<BookVo> {
        return myRepository.getMyReadBook()
    }
}