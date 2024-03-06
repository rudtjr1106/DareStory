package com.example.domain.usecase.book

import com.example.domain.base.UseCase
import com.example.domain.model.vo.BookResponseVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.repository.BookRepository
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetSearchBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
) : UseCase<String, BookResponseVo>() {
    override suspend operator fun invoke(request: String) : BookResponseVo {
        return bookRepository.searchBook(request)
    }
}