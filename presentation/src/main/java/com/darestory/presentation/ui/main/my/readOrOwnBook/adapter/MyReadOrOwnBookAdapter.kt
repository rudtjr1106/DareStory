package com.darestory.presentation.ui.main.my.readOrOwnBook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemDiscussionBookListBinding
import com.darestory.presentation.databinding.ItemMyOwnBookBinding
import com.darestory.presentation.ui.main.my.readOrOwnBook.viewHolder.MyOwnBookViewHolder
import com.darestory.presentation.ui.main.search.result.adapter.ResultSearchAdapter
import com.darestory.presentation.ui.main.search.result.viewHolder.BookItemViewHolder
import com.darestory.domain.model.enums.ReadOrOwnType
import com.darestory.domain.model.vo.MyBookVo
import com.darestory.domain.model.vo.MyReadOrOwnBookVo

class MyReadOrOwnBookAdapter(
    private val resultSearchListener: ResultSearchAdapter.ResultSearchDelegate,
    private val myReadOrOwnBookListener : MyReadOrOwnBookDelegate,
): ListAdapter<MyReadOrOwnBookVo, RecyclerView.ViewHolder>(ResultSearchDiffCallBack()) {

    interface MyReadOrOwnBookDelegate {
        fun onClickMyBook(item : MyBookVo)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookItemViewHolder -> holder.bind(currentList[position].bookVo)
            is MyOwnBookViewHolder -> holder.bind(currentList[position].myBookVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(ReadOrOwnType.valueOf(viewType)){
            ReadOrOwnType.READ_BOOK -> {
                val binding = ItemDiscussionBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BookItemViewHolder(binding, resultSearchListener)
            }
            ReadOrOwnType.SELECT_OWN_BOOK,
            ReadOrOwnType.OWN_BOOK -> {
                val binding = ItemMyOwnBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyOwnBookViewHolder(binding, myReadOrOwnBookListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.type
    }

}

class ResultSearchDiffCallBack : DiffUtil.ItemCallback<MyReadOrOwnBookVo>() {
    override fun areItemsTheSame(oldItem: MyReadOrOwnBookVo, newItem: MyReadOrOwnBookVo): Boolean = oldItem.type == newItem.type
    override fun areContentsTheSame(oldItem: MyReadOrOwnBookVo, newItem: MyReadOrOwnBookVo): Boolean = oldItem == newItem
}