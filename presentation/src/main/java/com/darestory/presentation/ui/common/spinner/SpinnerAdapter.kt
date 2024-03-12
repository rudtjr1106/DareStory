package com.darestory.presentation.ui.common.spinner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemSpinnerBinding

class SpinnerAdapter(private val listener: SpinnerDelegate) : ListAdapter<String, RecyclerView.ViewHolder>(
    ArchiveDiffCallback()
) {

    interface SpinnerDelegate {
        fun onItemClick(name: String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SpinnerViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpinnerViewHolder(binding, listener)
    }

}

class ArchiveDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem.length == newItem.length
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}