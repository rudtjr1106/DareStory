package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.MyOwnBookProseRequestVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class DeleteMyOwnBookProseUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<MyOwnBookProseRequestVo, Boolean>() {
    override suspend operator fun invoke(request: MyOwnBookProseRequestVo): Boolean {
        return myRepository.deleteMyOwnBookProse(request)
    }
}