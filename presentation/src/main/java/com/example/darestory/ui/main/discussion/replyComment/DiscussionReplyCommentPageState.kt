package com.example.darestory.ui.main.discussion.replyComment

import com.example.darestory.PageState
import com.example.domain.model.vo.DiscussionCommentPageVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DiscussionReplyCommentPageState(
    val commentList : StateFlow<List<DiscussionCommentPageVo>>,
    var commentEdit : MutableStateFlow<String>
) : PageState