package com.example.darestory.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.darestory.databinding.ItemTodayProseBinding
import com.example.darestory.ui.main.home.viewHolder.TodayProseViewPagerViewHolder
import com.example.domain.model.enums.HomeViewType
import com.example.domain.model.vo.ProseVo

class TodayProseViewPagerAdapter() : ListAdapter<ProseVo, RecyclerView.ViewHolder>(
    TodayProseDiffCallBack()
) {



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodayProseViewPagerViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTodayProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayProseViewPagerViewHolder(binding)
    }

}

class TodayProseDiffCallBack : DiffUtil.ItemCallback<ProseVo>() {
    override fun areItemsTheSame(oldItem: ProseVo, newItem: ProseVo): Boolean =
        oldItem.proseId == newItem.proseId && oldItem.content == newItem.content
    override fun areContentsTheSame(oldItem: ProseVo, newItem: ProseVo): Boolean =
        oldItem == newItem
}