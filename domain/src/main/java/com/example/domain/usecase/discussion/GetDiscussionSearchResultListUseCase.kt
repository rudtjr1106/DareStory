package com.example.domain.usecase.discussion

import com.example.domain.base.UseCase
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.SearchVo
import com.example.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetDiscussionSearchResultListUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<SearchVo, List<DiscussionVo>>() {
    override suspend operator fun invoke(request: SearchVo): List<DiscussionVo> {
        return discussionRepository.getSearchedResult(request)
    }
}