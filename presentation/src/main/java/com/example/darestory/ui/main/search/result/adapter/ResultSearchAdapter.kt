package com.example.darestory.ui.main.search.result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.databinding.ItemDiscussionBookListBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.home.viewHolder.HomeNormalProseViewHolder
import com.example.darestory.ui.main.search.result.viewHolder.BookItemViewHolder
import com.example.domain.model.enums.DetailPageViewType
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.BookVo
import com.example.domain.model.vo.ProseVo
import com.example.domain.model.vo.ResultSearchVo

class ResultSearchAdapter(
    private val homeListener: HomeAdapter.HomeDelegate,
    private val resultSearchListener : ResultSearchDelegate)
    : ListAdapter<ResultSearchVo, RecyclerView.ViewHolder>(ResultSearchDiffCallBack()) {

    interface ResultSearchDelegate {
        fun onClickBook(item : BookVo)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeNormalProseViewHolder -> holder.bind(currentList[position].proseVo)
            is BookItemViewHolder -> holder.bind(currentList[position].bookVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(DetailType.valueOf(viewType)){
            DetailType.PROSE -> {
                val binding = ItemAllProseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeNormalProseViewHolder(binding, homeListener)
            }
            DetailType.DISCUSSION -> TODO()
            DetailType.BOOK -> {
                val binding = ItemDiscussionBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BookItemViewHolder(binding, resultSearchListener)
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