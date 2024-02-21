package com.example.darestory.ui.common.spinner

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemSpinnerBinding

class SpinnerViewHolder(
    private val binding: ItemSpinnerBinding,
    private val listener: SpinnerAdapter.SpinnerDelegate
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.apply {
            textItem.setOnClickListener {
                listener.onItemClick(textItem.text.toString())
            }
        }
    }

    fun bind(item: String) {
        binding.apply {
            textItem.text = item
        }
    }
}