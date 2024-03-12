package com.darestory.domain.usecase.my

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.MyOwnBookProseRequestVo
import com.darestory.domain.repository.MyRepository
import javax.inject.Inject

class DeleteMyOwnBookProseUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<MyOwnBookProseRequestVo, Boolean>() {
    override suspend operator fun invoke(request: MyOwnBookProseRequestVo): Boolean {
        return myRepository.deleteMyOwnBookProse(request)
    }
}