package com.darestory.presentation.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemBottomSheetBinding
import com.darestory.presentation.ui.common.viewHolder.BottomSheetMenuItemViewHolder
import com.darestory.domain.model.enums.BottomSheetMenuItemType

class BottomSheetMenuAdapter(
    private val listener: Delegate,
) : ListAdapter<BottomSheetMenuItemType, RecyclerView.ViewHolder>(BottomSheetMenuDiffCallback()) {

    interface Delegate {
        fun onClickMenu(type: BottomSheetMenuItemType)
        val selectedDialogMenuItem:BottomSheetMenuItemType?
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BottomSheetMenuItemViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetMenuItemViewHolder(binding,listener)
    }
}

class BottomSheetMenuDiffCallback : DiffUtil.ItemCallback<BottomSheetMenuItemType>() {
    override fun areItemsTheSame(oldItem: BottomSheetMenuItemType, newItem: BottomSheetMenuItemType): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: BottomSheetMenuItemType, newItem: BottomSheetMenuItemType): Boolean = oldItem == newItem
}