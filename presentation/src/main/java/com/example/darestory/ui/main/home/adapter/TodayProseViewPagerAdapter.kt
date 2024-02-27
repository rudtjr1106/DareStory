package com.example.darestory.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemTodayProseBinding
import com.example.darestory.ui.main.home.viewHolder.TodayProseViewPagerViewHolder

class TodayProseViewPagerAdapter() : ListAdapter<String, RecyclerView.ViewHolder>(
    OnboardingDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodayProseViewPagerViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTodayProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayProseViewPagerViewHolder(binding)
    }

}

class OnboardingDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}