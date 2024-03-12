package com.darestory.domain.usecase.home

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.model.vo.SearchVo
import com.darestory.domain.repository.ProseRepository
import javax.inject.Inject

class GetProseSearchResultListUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<SearchVo, List<ProseVo>>() {
    override suspend operator fun invoke(request: SearchVo): List<ProseVo> {
        return proseRepository.getSearchedResult(request)
    }
}