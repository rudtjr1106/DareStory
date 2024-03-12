package com.darestory.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemAllProseBinding
import com.darestory.presentation.databinding.ItemLayoutHomeTodayProseBinding
import com.darestory.presentation.ui.main.home.viewHolder.HomeNormalProseViewHolder
import com.darestory.presentation.ui.main.home.viewHolder.HomeTodayProseViewHolder
import com.darestory.domain.model.enums.HomeViewType
import com.darestory.domain.model.enums.SortType
import com.darestory.domain.model.vo.HomeProseVo

class HomeAdapter(private val listener: HomeDelegate) : ListAdapter<HomeProseVo, RecyclerView.ViewHolder>(
    HomeItemDiffCallBack()
){

    var sortType : SortType = SortType.POPULAR

    interface HomeDelegate {
        fun onClickSearch()
        fun onClickProse(proseId : Int)
        fun onClickSort(type : SortType)
        fun onClickWriteProse()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeTodayProseViewHolder -> holder.bind(currentList[position].proseListVo)
            is HomeNormalProseViewHolder -> holder.bind(currentList[position].allProseVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(HomeViewType.valueOf(viewType)){
            HomeViewType.TODAY_PROSE -> {
                val binding = ItemLayoutHomeTodayProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeTodayProseViewHolder(binding, listener, sortType)
            }
            HomeViewType.NORMAL_PROSE -> {
                val binding = ItemAllProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeNormalProseViewHolder(binding, listener)
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