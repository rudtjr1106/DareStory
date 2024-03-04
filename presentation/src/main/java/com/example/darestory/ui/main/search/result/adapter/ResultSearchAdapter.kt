package com.example.darestory.ui.main.search.result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.home.viewHolder.HomeNormalProseViewHolder
import com.example.domain.model.vo.ProseVo

class ResultSearchAdapter(private val homeListener: HomeAdapter.HomeDelegate) : ListAdapter<ProseVo, RecyclerView.ViewHolder>(
    ResultSearchDiffCallBack()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeNormalProseViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAllProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeNormalProseViewHolder(binding, homeListener)
    }

}

class ResultSearchDiffCallBack : DiffUtil.ItemCallback<ProseVo>() {
    override fun areItemsTheSame(oldItem: ProseVo, newItem: ProseVo): Boolean = oldItem.proseId == newItem.proseId
    override fun areContentsTheSame(oldItem: ProseVo, newItem: ProseVo): Boolean = oldItem == newItem
}