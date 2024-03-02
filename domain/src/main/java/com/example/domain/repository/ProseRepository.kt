package com.example.domain.repository

import com.example.domain.model.vo.UpdateCommentVo
import com.example.domain.model.vo.LikeVo
import com.example.domain.model.vo.ProseVo

interface ProseRepository {
    suspend fun getAllProse() : List<ProseVo>
    suspend fun getProse(request : Int) : ProseVo
    suspend fun likeCancel(request: LikeVo) : Boolean
    suspend fun likeAdd(request: LikeVo) : Boolean
    suspend fun addComment(request: UpdateCommentVo) : Boolean
    suspend fun deleteComment(request: UpdateCommentVo) : Boolean
    suspend fun upload(request: ProseVo) : Boolean
    suspend fun update(request: ProseVo) : Boolean
    suspend fun deleteProse(request: Int) : Boolean
}