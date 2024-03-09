package com.example.domain.repository

import com.example.domain.model.vo.MyOwnBookProseRequestVo
import com.example.domain.model.vo.BookVo
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.MyBookVo
import com.example.domain.model.vo.NoticeVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.UserVo

interface MyRepository {
    suspend fun getMyInfo(request : String) : UserVo
    suspend fun getMyDiscussion() : List<DiscussionVo>
    suspend fun getMyProse() : List<ProseVo>
    suspend fun getMyOwnBook() : List<MyBookVo>
    suspend fun getMyReadBook() : List<BookVo>
    suspend fun addMyReadBook(request : BookVo) : Boolean
    suspend fun addMyOwnBook(request : MyBookVo) : Boolean
    suspend fun getMyOwnBookProse(request: String) : List<ProseVo>
    suspend fun addMyOwnBookProse(request: MyOwnBookProseRequestVo) : Boolean
    suspend fun deleteMyOwnBookProse(request: MyOwnBookProseRequestVo) : Boolean
    suspend fun getNoticeList() : List<NoticeVo>
}