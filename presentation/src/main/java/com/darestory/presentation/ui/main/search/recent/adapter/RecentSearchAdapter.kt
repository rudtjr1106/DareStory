package com.darestory.presentation.ui.main.search.recent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemRecentSearchBinding
import com.darestory.presentation.ui.main.search.recent.viewHolder.RecentSearchViewHolder

class RecentSearchAdapter(private val listener: RecentSearchDelegate) : ListAdapter<String, RecyclerView.ViewHolder>(
    RecentSearchDiffCallBack()
) {

    interface RecentSearchDelegate {
        fun onClickItem(recent : String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentSearchViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchViewHolder(binding, listener)
    }

}

class RecentSearchDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}