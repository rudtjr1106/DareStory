package com.example.domain.repository

import com.example.domain.model.vo.DiscussionVo

interface DiscussionRepository {
    suspend fun getAllDiscussion() : List<DiscussionVo>
    suspend fun upload(request: DiscussionVo) : Boolean
    suspend fun update(request: DiscussionVo) : Boolean
    suspend fun getDiscussion(request : Int) : DiscussionVo
}