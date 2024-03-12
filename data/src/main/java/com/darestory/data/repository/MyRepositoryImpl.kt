package com.darestory.data.repository

import com.darestory.data.DataUserInfo
import com.darestory.data.EndPoints
import com.darestory.domain.model.vo.MyOwnBookProseRequestVo
import com.darestory.domain.model.vo.BookVo
import com.darestory.domain.model.vo.DiscussionVo
import com.darestory.domain.model.vo.MyBookVo
import com.darestory.domain.model.vo.NoticeVo
import com.darestory.domain.model.vo.ProseVo
import com.darestory.domain.model.vo.UserVo
import com.darestory.domain.repository.MyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyRepositoryImpl @Inject constructor() : MyRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val myDbRef = db.getReference(EndPoints.AUTH)
    override suspend fun getMyInfo(request: String): UserVo = suspendCoroutine { coroutineScope ->
        myDbRef.child(request).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val userVo = snapshot.getValue(UserVo::class.java)
                    userVo?.let {
                        DataUserInfo.updateInfo(it)
                        coroutineScope.resume(it)
                    }
                }
                else{
                    coroutineScope.resume(UserVo())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(UserVo())
            }
        })
    }

    override suspend fun getMyDiscussion(): List<DiscussionVo> = suspendCoroutine { coroutineScope ->
        val discussionList: MutableList<DiscussionVo> = mutableListOf()
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_DISCUSSION)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val discussion = dataSnapshot.getValue(DiscussionVo::class.java)
                    discussion?.let {
                        discussionList.add(it)
                    }
                }
                coroutineScope.resume(discussionList)
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(emptyList())
            }
        })
    }

    override suspend fun getMyProse(): List<ProseVo> = suspendCoroutine { coroutineScope ->
        val proseList: MutableList<ProseVo> = mutableListOf()
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_PROSE)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val prose = dataSnapshot.getValue(ProseVo::class.java)
                        prose?.let {
                            proseList.add(it)
                        }
                    }
                    coroutineScope.resume(proseList)
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.resume(emptyList())
                }
            })
    }

    override suspend fun getMyOwnBook(): List<MyBookVo> = suspendCoroutine { coroutineScope ->
        val myBookList: MutableList<MyBookVo> = mutableListOf()
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_BOOK)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val myBook = dataSnapshot.getValue(MyBookVo::class.java)
                        myBook?.let {
                            myBookList.add(it)
                        }
                    }
                    coroutineScope.resume(myBookList)
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.resume(emptyList())
                }
            })
    }

    override suspend fun getMyReadBook(): List<BookVo> = suspendCoroutine { coroutineScope ->
        val bookList: MutableList<BookVo> = mutableListOf()
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_READ_BOOK)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val book = dataSnapshot.getValue(BookVo::class.java)
                        book?.let {
                            bookList.add(it)
                        }
                    }
                    coroutineScope.resume(bookList)
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.resume(emptyList())
                }
            })
    }

    override suspend fun addMyReadBook(request: BookVo): Boolean = suspendCoroutine {
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_READ_BOOK).child(request.title).setValue(request)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    it.resume(true)
                }
                else{
                    it.resume(false)
                }
            }
    }

    override suspend fun addMyOwnBook(request: MyBookVo): Boolean = suspendCoroutine {
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_BOOK).child(request.myBookTitle).setValue(request)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    it.resume(true)
                }
                else{
                    it.resume(false)
                }
            }
    }

    override suspend fun addMyOwnBookProse(request: MyOwnBookProseRequestVo): Boolean = suspendCoroutine {
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_BOOK).child(request.title).child(EndPoints.PROSE)
            .child(request.proseVo.proseId.toString() + "번").setValue(request.proseVo)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    it.resume(true)
                }
                else{
                    it.resume(false)
                }
            }
    }

    override suspend fun deleteMyOwnBookProse(request: MyOwnBookProseRequestVo): Boolean = suspendCoroutine {
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_BOOK).child(request.title).child(EndPoints.PROSE)
            .child(request.proseVo.proseId.toString() + "번").removeValue()
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    it.resume(true)
                }
                else{
                    it.resume(false)
                }
            }
    }

    override suspend fun getNoticeList(): List<NoticeVo>  = suspendCoroutine { coroutineScope ->
        val noticeList: MutableList<NoticeVo> = mutableListOf()
        db.getReference(EndPoints.NOTICE)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val notice = dataSnapshot.getValue(NoticeVo::class.java)
                        notice?.let {
                            noticeList.add(it)
                        }
                    }
                    coroutineScope.resume(noticeList)
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.resume(emptyList())
                }
            })
    }

    override suspend fun getNoticeDetail(request: Int): NoticeVo = suspendCoroutine { coroutineScope ->
        db.getReference(EndPoints.NOTICE).child(request.toString() + "번")
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notice = snapshot.getValue(NoticeVo::class.java)
                notice?.let {
                    coroutineScope.resume(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.resume(NoticeVo())
            }
        })
    }


    override suspend fun getMyOwnBookProse(request: String): List<ProseVo> = suspendCoroutine { coroutineScope ->
        val proseList: MutableList<ProseVo> = mutableListOf()
        myDbRef.child(DataUserInfo.info.nickName).child(EndPoints.MY_BOOK).child(request).child(EndPoints.PROSE)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val prose = dataSnapshot.getValue(ProseVo::class.java)
                        prose?.let {
                            proseList.add(it)
                        }
                    }
                    coroutineScope.resume(proseList)
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.resume(emptyList())
                }
            })
    }

}