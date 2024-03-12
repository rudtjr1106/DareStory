package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.MyBookVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class GetMyOwnBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<MyBookVo>>() {
    override suspend operator fun invoke(request: Unit): List<MyBookVo> {
        return myRepository.getMyOwnBook()
    }
}