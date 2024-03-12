package com.darestory.presentation.ui.main.discussion.replyComment

import com.darestory.presentation.PageState
import com.darestory.domain.model.vo.DiscussionCommentPageVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DiscussionReplyCommentPageState(
    val commentList : StateFlow<List<DiscussionCommentPageVo>>,
    var commentEdit : MutableStateFlow<String>
) : PageState