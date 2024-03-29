package com.darestory.presentation.ui.main.search.recent.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemRecentSearchBinding
import com.darestory.presentation.ui.main.search.recent.adapter.RecentSearchAdapter

class RecentSearchViewHolder(
    private val binding: ItemRecentSearchBinding,
    private val listener: RecentSearchAdapter.RecentSearchDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var content : String

    init {
        binding.apply {
            textRecentSearchItem.setOnClickListener {
                listener.onClickItem(content)
            }
        }
    }

    fun bind(item : String) {
        content = item
        binding.apply {
            textRecentSearchItem.text = item
        }
    }
}