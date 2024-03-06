package com.example.domain.repository

import com.example.domain.model.vo.CommentRequestVo
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.DisCommentVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.UpdateCommentVo
import com.example.domain.model.vo.UpdateReplyCommentVo

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
}