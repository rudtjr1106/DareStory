package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.MyBookVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class AddMyOwnBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<MyBookVo, Boolean>() {
    override suspend operator fun invoke(request: MyBookVo): Boolean {
        return myRepository.addMyOwnBook(request)
    }
}