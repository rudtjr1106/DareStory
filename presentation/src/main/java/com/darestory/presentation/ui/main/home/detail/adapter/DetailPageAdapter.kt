package com.darestory.presentation.ui.main.home.detail.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemDetailCommentEmptyBinding
import com.darestory.presentation.databinding.ItemDetailDiscussionCommentBinding
import com.darestory.presentation.databinding.ItemDetailProseCommentBinding
import com.darestory.presentation.databinding.ItemLayoutDetailBinding
import com.darestory.presentation.ui.main.home.detail.viewHolder.DetailPageViewHolder
import com.darestory.presentation.ui.main.home.detail.viewHolder.DiscussionCommentViewHolder
import com.darestory.presentation.ui.main.home.detail.viewHolder.EmptyCommentViewHolder
import com.darestory.presentation.ui.main.home.detail.viewHolder.ProseAuthorCommentViewHolder
import com.darestory.presentation.ui.main.home.detail.viewHolder.ProseCommentViewHolder
import com.darestory.domain.model.enums.DetailPageViewType
import com.darestory.domain.model.vo.DetailPageVo
import com.darestory.domain.model.vo.DisCommentVo

class DetailPageAdapter(private val listener: DetailPageDelegate) : ListAdapter<DetailPageVo, RecyclerView.ViewHolder>(
    DetailItemDiffCallBack()
){

    interface DetailPageDelegate {
        fun onClickLike(id : Int, isLiked : Boolean)
        fun onClickBack()
        fun onClickMenu(author: String)
        fun onClickCommentMenu(commentId : Int, writer : String)
        fun onClickReplyComment(commentVo: DisCommentVo)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailPageViewHolder -> holder.bind(currentList[position].detailContent)
            is ProseCommentViewHolder -> holder.bind(currentList[position].proseComment)
            is ProseAuthorCommentViewHolder -> holder.bind(currentList[position].proseComment)
            is DiscussionCommentViewHolder -> holder.bind(currentList[position].discussionComment)
            is EmptyCommentViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(DetailPageViewType.valueOf(viewType)){
            DetailPageViewType.CONTENT -> {
                val binding = ItemLayoutDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DetailPageViewHolder(binding, listener)
            }
            DetailPageViewType.PROSE_COMMENT -> {
                val binding = ItemDetailProseCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProseCommentViewHolder(binding, listener)
            }

            DetailPageViewType.PROSE_AUTHOR_COMMENT -> {
                val binding = ItemDetailProseCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProseAuthorCommentViewHolder(binding)
            }

            DetailPageViewType.DISCUSSION_COMMENT -> {
                val binding = ItemDetailDiscussionCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DiscussionCommentViewHolder(binding, listener)
            }

            DetailPageViewType.EMPTY -> {
                val binding = ItemDetailCommentEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EmptyCommentViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].detailViewType.type
    }
}

class DetailItemDiffCallBack : DiffUtil.ItemCallback<DetailPageVo>() {
    override fun areItemsTheSame(oldItem: DetailPageVo, newItem: DetailPageVo): Boolean =
        oldItem.detailViewType == newItem.detailViewType && oldItem.detailContent == newItem.detailContent
    override fun areContentsTheSame(oldItem: DetailPageVo, newItem: DetailPageVo): Boolean =
        oldItem == newItem
}