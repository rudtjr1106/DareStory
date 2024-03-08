package com.example.domain.repository

import com.example.domain.model.vo.BookVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.MyBookVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.UserVo

interface MyRepository {
    suspend fun getMyInfo(request : String) : UserVo
    suspend fun getMyDiscussion() : List<DiscussionVo>
    suspend fun getMyProse() : List<ProseVo>
    suspend fun getMyOwnBook() : List<MyBookVo>
    suspend fun getMyReadBook() : List<BookVo>
}