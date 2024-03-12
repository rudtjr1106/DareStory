package com.darestory.presentation.ui.main.discussion.replyComment.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemDetailDiscussionCommentBinding
import com.darestory.presentation.databinding.ItemDetailDiscussionReplyCommentBinding
import com.darestory.presentation.ui.main.discussion.replyComment.adapter.viewHolder.DiscussionCommentDetailViewHolder
import com.darestory.presentation.ui.main.discussion.replyComment.adapter.viewHolder.DiscussionReplyCommentViewHolder
import com.darestory.domain.model.enums.CommentType
import com.darestory.domain.model.vo.DiscussionCommentPageVo

class DiscussionCommentAdapter(private val listener: DiscussionCommentDelegate) : ListAdapter<DiscussionCommentPageVo, RecyclerView.ViewHolder>(
    DetailItemDiffCallBack()
){

    interface DiscussionCommentDelegate {
        fun onClickCommentMenu(commentId : Int, writer : String, type : CommentType)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DiscussionCommentDetailViewHolder -> holder.bind(currentList[position].commentVo)
            is DiscussionReplyCommentViewHolder -> holder.bind(currentList[position].commentVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(CommentType.valueOf(viewType)){
            CommentType.NORMAL -> {
                val binding = ItemDetailDiscussionCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DiscussionCommentDetailViewHolder(binding, listener)
            }
            CommentType.REPLY -> {
                val binding = ItemDetailDiscussionReplyCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DiscussionReplyCommentViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.type
    }
}

class DetailItemDiffCallBack : DiffUtil.ItemCallback<DiscussionCommentPageVo>() {
    override fun areItemsTheSame(oldItem: DiscussionCommentPageVo, newItem: DiscussionCommentPageVo): Boolean =
        oldItem.type == newItem.type && oldItem.commentVo == newItem.commentVo
    override fun areContentsTheSame(oldItem: DiscussionCommentPageVo, newItem: DiscussionCommentPageVo): Boolean =
        oldItem == newItem
}