package com.darestory.domain.usecase.discussion

import com.darestory.domain.base.UseCase
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.model.vo.SearchVo
import com.darestory.domain.repository.DiscussionRepository
import javax.inject.Inject

class GetDiscussionSearchResultListUseCase @Inject constructor(
    private val discussionRepository: DiscussionRepository
) : UseCase<SearchVo, List<DiscussionVo>>() {
    override suspend operator fun invoke(request: SearchVo): List<DiscussionVo> {
        return discussionRepository.getSearchedResult(request)
    }
}