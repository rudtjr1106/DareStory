package com.darestory.domain.usecase.book

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.BookResponseVo
import com.darestory.domain.repository.BookRepository
import javax.inject.Inject

class GetSearchBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
) : UseCase<String, BookResponseVo>() {
    override suspend operator fun invoke(request: String) : BookResponseVo {
        return bookRepository.searchBook(request)
    }
}