package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.BookVo
import com.example.domain.model.vo.MyBookVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class AddMyOwnBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<MyBookVo, Boolean>() {
    override suspend operator fun invoke(request: MyBookVo): Boolean {
        return myRepository.addMyOwnBook(request)
    }
}