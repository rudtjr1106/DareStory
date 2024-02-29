package com.example.darestory.ui.main.home.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.databinding.ItemDetailProseCommentBinding
import com.example.darestory.databinding.ItemLayoutDetailBinding
import com.example.darestory.databinding.ItemLayoutHomeTodayProseBinding
import com.example.darestory.ui.main.home.detail.viewHolder.DetailPageViewHolder
import com.example.darestory.ui.main.home.detail.viewHolder.ProseCommentViewHolder
import com.example.darestory.ui.main.home.viewHolder.HomeAllProseViewHolder
import com.example.darestory.ui.main.home.viewHolder.HomeTodayProseViewHolder
import com.example.domain.model.enums.DetailPageViewType
import com.example.domain.model.enums.HomeViewType
import com.example.domain.model.vo.DetailPageVo

class DetailPageAdapter(private val listener: DetailPageDelegate) : ListAdapter<DetailPageVo, RecyclerView.ViewHolder>(
    DetailItemDiffCallBack()
){

    interface DetailPageDelegate {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailPageViewHolder -> holder.bind(currentList[position].detailContent)
            is ProseCommentViewHolder -> holder.bind(currentList[position].proseComment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(DetailPageViewType.valueOf(viewType)){
            DetailPageViewType.CONTENT -> {
                val binding = ItemLayoutDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DetailPageViewHolder(binding)
            }
            DetailPageViewType.PROSE_COMMENT -> {
                val binding = ItemDetailProseCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProseCommentViewHolder(binding)
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