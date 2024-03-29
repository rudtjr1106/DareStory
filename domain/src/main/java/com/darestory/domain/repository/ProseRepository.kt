package com.darestory.domain.repository

import com.darestory.domain.model.vo.UpdateCommentVo
import com.darestory.domain.model.vo.LikeVo
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.model.vo.SearchVo

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
    suspend fun getRecentSearch() : List<String>
    suspend fun insertRecentSearch(request : String) : Boolean
    suspend fun getSearchedResult(request: SearchVo) : List<ProseVo>
}