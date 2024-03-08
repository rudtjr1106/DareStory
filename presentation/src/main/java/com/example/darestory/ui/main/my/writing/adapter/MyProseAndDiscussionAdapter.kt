package com.example.darestory.ui.main.my.writing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.databinding.ItemDiscussionBookListBinding
import com.example.darestory.databinding.ItemMyOwnBookProseBinding
import com.example.darestory.ui.main.discussion.adapter.DiscussionAdapter
import com.example.darestory.ui.main.discussion.viewHolder.DiscussionItemViewHolder
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.home.viewHolder.HomeNormalProseViewHolder
import com.example.darestory.ui.main.my.writing.viewHolder.MyOwnBookProseViewHolder
import com.example.domain.model.enums.DetailType
import com.example.domain.model.vo.ResultSearchVo

class MyProseAndDiscussionAdapter(
    private val homeListener: HomeAdapter.HomeDelegate,
    private val discussionListener : DiscussionAdapter.DiscussionDelegate
)
    : ListAdapter<ResultSearchVo, RecyclerView.ViewHolder>(ResultSearchDiffCallBack()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeNormalProseViewHolder -> holder.bind(currentList[position].proseVo)
            is DiscussionItemViewHolder -> holder.bind(currentList[position].discussionVo)
            is MyOwnBookProseViewHolder -> holder.bind(currentList[position].proseVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(DetailType.valueOf(viewType)){
            DetailType.PROSE -> {
                val binding = ItemAllProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeNormalProseViewHolder(binding, homeListener)
            }
            DetailType.DISCUSSION -> {
                val binding = ItemDiscussionBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DiscussionItemViewHolder(binding, discussionListener)
            }
            DetailType.BOOK -> {
                val binding = ItemMyOwnBookProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyOwnBookProseViewHolder(binding, homeListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.type
    }

}

class ResultSearchDiffCallBack : DiffUtil.ItemCallback<ResultSearchVo>() {
    override fun areItemsTheSame(oldItem: ResultSearchVo, newItem: ResultSearchVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: ResultSearchVo, newItem: ResultSearchVo): Boolean = oldItem == newItem
}