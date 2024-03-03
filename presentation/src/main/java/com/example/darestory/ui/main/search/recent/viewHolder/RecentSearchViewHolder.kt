package com.example.darestory.ui.main.search.recent.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemRecentSearchBinding
import com.example.darestory.ui.main.search.recent.adapter.RecentSearchAdapter

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

    fun bind(item : String, position : Int) {
        content = item
        binding.apply {
            textNumber.text = position.toString()
            textRecentSearchItem.text = item
        }
    }
}