package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.MyBookVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class GetMyOwnBookUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<Unit, List<MyBookVo>>() {
    override suspend operator fun invoke(request: Unit): List<MyBookVo> {
        return myRepository.getMyOwnBook()
    }
}