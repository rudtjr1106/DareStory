package com.example.darestory.ui.main.discussion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemDiscussionBookListBinding
import com.example.darestory.databinding.ItemLayoutDiscussionTopBinding
import com.example.darestory.ui.main.discussion.viewHolder.DiscussionItemViewHolder
import com.example.darestory.ui.main.discussion.viewHolder.DiscussionTopViewHolder
import com.example.domain.model.enums.DiscussionViewType
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.MainDiscussionVo

class DiscussionAdapter(private val listener: DiscussionDelegate) : ListAdapter<MainDiscussionVo, RecyclerView.ViewHolder>(
    DiscussionDiffCallBack()
){

    var sortType : SortType = SortType.POPULAR
    interface DiscussionDelegate {
        fun onClickSearch()
        fun onClickDiscussion(disId : Int)
        fun onClickSort(type : SortType)
        fun onClickWriteDiscussion()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DiscussionTopViewHolder -> holder.bind()
            is DiscussionItemViewHolder -> holder.bind(currentList[position].discussionVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(DiscussionViewType.valueOf(viewType)){
            DiscussionViewType.TOP -> {
                val binding = ItemLayoutDiscussionTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DiscussionTopViewHolder(binding, listener)
            }
            DiscussionViewType.DISCUSSION -> {
                val binding = ItemDiscussionBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DiscussionItemViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class DiscussionDiffCallBack : DiffUtil.ItemCallback<MainDiscussionVo>() {
    override fun areItemsTheSame(oldItem: MainDiscussionVo, newItem: MainDiscussionVo): Boolean =
        oldItem.viewType == newItem.viewType && oldItem.discussionVo == newItem.discussionVo
    override fun areContentsTheSame(oldItem: MainDiscussionVo, newItem: MainDiscussionVo): Boolean =
        oldItem == newItem
}