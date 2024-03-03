package com.example.domain.usecase.home

import com.example.domain.base.UseCase
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.SearchVo
import com.example.domain.repository.ProseRepository
import javax.inject.Inject

class GetProseSearchResultListUseCase @Inject constructor(
    private val proseRepository: ProseRepository
) : UseCase<SearchVo, List<ProseVo>>() {
    override suspend operator fun invoke(request: SearchVo): List<ProseVo> {
        return proseRepository.getSearchedResult(request)
    }
}