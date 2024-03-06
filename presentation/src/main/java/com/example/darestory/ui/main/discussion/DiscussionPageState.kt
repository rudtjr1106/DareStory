package com.example.darestory.ui.main.discussion

import com.example.darestory.PageState
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.MainDiscussionVo
import kotlinx.coroutines.flow.StateFlow

data class DiscussionPageState(
    val discussionList : StateFlow<List<MainDiscussionVo>>,
    val sortedType : StateFlow<SortType>
) : PageState