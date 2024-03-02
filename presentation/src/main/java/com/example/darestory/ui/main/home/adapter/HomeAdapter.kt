package com.example.darestory.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.databinding.ItemLayoutHomeTodayProseBinding
import com.example.darestory.ui.main.home.viewHolder.HomeAllProseViewHolder
import com.example.darestory.ui.main.home.viewHolder.HomeTodayProseViewHolder
import com.example.domain.model.enums.HomeViewType
import com.example.domain.model.vo.HomeProseVo
import com.example.domain.model.vo.ProseVo

class HomeAdapter(private val listener: HomeDelegate) : ListAdapter<HomeProseVo, RecyclerView.ViewHolder>(
    HomeItemDiffCallBack()
){

    interface HomeDelegate {
        fun onClickProse(proseId : Int)
        fun onClickSortPopular()
        fun onClickSortRecent()
        fun onClickSortAge()
        fun onClickWriteProse()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeTodayProseViewHolder -> holder.bind(currentList[position].proseListVo)
            is HomeAllProseViewHolder -> holder.bind(currentList[position].allProseVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(HomeViewType.valueOf(viewType)){
            HomeViewType.TODAY_PROSE -> {
                val binding = ItemLayoutHomeTodayProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeTodayProseViewHolder(binding, listener)
            }
            HomeViewType.ALL_PROSE -> {
                val binding = ItemAllProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeAllProseViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].homeViewType.type
    }
}

class HomeItemDiffCallBack : DiffUtil.ItemCallback<HomeProseVo>() {
    override fun areItemsTheSame(oldItem: HomeProseVo, newItem: HomeProseVo): Boolean =
        oldItem.homeViewType == newItem.homeViewType && oldItem.proseListVo == newItem.proseListVo
    override fun areContentsTheSame(oldItem: HomeProseVo, newItem: HomeProseVo): Boolean =
        oldItem == newItem
}