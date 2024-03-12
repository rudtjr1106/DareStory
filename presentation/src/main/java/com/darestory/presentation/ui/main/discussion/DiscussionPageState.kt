package com.darestory.presentation.ui.main.discussion

import com.darestory.presentation.PageState
import com.darestory.domain.model.enums.SortType
import com.darestory.domain.model.vo.MainDiscussionVo
import kotlinx.coroutines.flow.StateFlow

data class DiscussionPageState(
    val discussionList : StateFlow<List<MainDiscussionVo>>,
    val sortedType : StateFlow<SortType>
) : PageState