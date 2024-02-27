package com.example.darestory.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemLayoutHomeAllProseBinding
import com.example.darestory.databinding.ItemLayoutHomeTodayProseBinding
import com.example.darestory.ui.main.home.viewHolder.HomeAllProseViewHolder
import com.example.darestory.ui.main.home.viewHolder.HomeTodayProseViewHolder
import com.example.domain.model.enums.HomeViewType

class HomeAdapter(private val listener: HomeDelegate) : ListAdapter<HomeViewType, RecyclerView.ViewHolder>(
    HomeItemDiffCallBack()
){

    interface HomeDelegate {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeTodayProseViewHolder -> holder.bind()
            is HomeAllProseViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(HomeViewType.valueOf(viewType)){
            HomeViewType.TODAY_PROSE -> {
                val binding = ItemLayoutHomeTodayProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeTodayProseViewHolder(binding)
            }
            HomeViewType.ALL_PROSE -> {
                val binding = ItemLayoutHomeAllProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeAllProseViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
    }
}

class HomeItemDiffCallBack : DiffUtil.ItemCallback<HomeViewType>() {
    override fun areItemsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
        oldItem.type == newItem.type && oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
        oldItem == newItem
}