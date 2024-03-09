package com.example.darestory.ui.main.my.notice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemMyOwnBookBinding
import com.example.darestory.ui.main.my.notice.viewHolder.NoticeItemViewHolder
import com.example.domain.model.vo.NoticeVo

class NoticeAdapter(
    private val listener: NoticeDelegate,
): ListAdapter<NoticeVo, RecyclerView.ViewHolder>(NoticeDiffCallBack()) {

    interface NoticeDelegate {
        fun onClickNotice(noticeId : Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoticeItemViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMyOwnBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeItemViewHolder(binding, listener)
    }
}

class NoticeDiffCallBack : DiffUtil.ItemCallback<NoticeVo>() {
    override fun areItemsTheSame(oldItem: NoticeVo, newItem: NoticeVo): Boolean = oldItem.noticeId == newItem.noticeId
    override fun areContentsTheSame(oldItem: NoticeVo, newItem: NoticeVo): Boolean = oldItem == newItem
}