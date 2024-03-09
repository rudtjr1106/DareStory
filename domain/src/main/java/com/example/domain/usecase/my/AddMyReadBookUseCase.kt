package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.BookVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class AddMyReadBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<BookVo, Boolean>() {
    override suspend operator fun invoke(request: BookVo): Boolean {
        return myRepository.addMyReadBook(request)
    }
}