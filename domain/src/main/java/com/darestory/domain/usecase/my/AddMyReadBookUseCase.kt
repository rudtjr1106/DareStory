package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.BookVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class AddMyReadBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<BookVo, Boolean>() {
    override suspend operator fun invoke(request: BookVo): Boolean {
        return myRepository.addMyReadBook(request)
    }
}