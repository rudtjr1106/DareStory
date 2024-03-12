package com.darestory.domain.repository

import com.darestory.domain.model.vo.CommentRequestVo
import com.darestory.domain.model.vo.DisCommentVo
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.model.vo.LikeVo
import com.darestory.domain.model.vo.SearchVo
import com.darestory.domain.model.vo.UpdateCommentVo
import com.darestory.domain.model.vo.UpdateReplyCommentVo

interface DiscussionRepository {
    suspend fun getAllDiscussion() : List<DiscussionVo>
    suspend fun upload(request: DiscussionVo) : Boolean
    suspend fun update(request: DiscussionVo) : Boolean
    suspend fun getDiscussion(request : Int) : DiscussionVo
    suspend fun addComment(request: UpdateCommentVo) : Boolean
    suspend fun addReplyComment(request: UpdateReplyCommentVo) : Boolean
    suspend fun deleteComment(request: UpdateCommentVo) : Boolean
    suspend fun deleteReplyComment(request: UpdateReplyCommentVo) : Boolean
    suspend fun likeCancel(request: LikeVo) : Boolean
    suspend fun likeAdd(request: LikeVo) : Boolean
    suspend fun deleteDiscussion(request : Int) : Boolean
    suspend fun getDiscussionReplyComment(request: CommentRequestVo) : DisCommentVo
    suspend fun getRecentSearch() : List<String>
    suspend fun insertRecentSearch(request : String) : Boolean
    suspend fun getSearchedResult(request: SearchVo) : List<DiscussionVo>
}