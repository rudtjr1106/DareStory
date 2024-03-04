package com.example.domain.repository

import com.example.domain.model.vo.DiscussionVo
interface DiscussionRepository {
    suspend fun getAllDiscussion() : List<DiscussionVo>
}