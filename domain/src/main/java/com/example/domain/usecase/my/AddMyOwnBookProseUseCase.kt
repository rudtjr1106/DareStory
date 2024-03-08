package com.example.domain.usecase.my

import com.example.domain.base.UseCase
import com.example.domain.model.vo.AddMyOwnBookProseRequestVo
import com.example.domain.repository.MyRepository
import javax.inject.Inject

class AddMyOwnBookProseUseCase @Inject constructor(
    private val myRepository: MyRepository
) : UseCase<AddMyOwnBookProseRequestVo, Boolean>() {
    override suspend operator fun invoke(request: AddMyOwnBookProseRequestVo): Boolean {
        return myRepository.addMyOwnBookProse(request)
    }
}