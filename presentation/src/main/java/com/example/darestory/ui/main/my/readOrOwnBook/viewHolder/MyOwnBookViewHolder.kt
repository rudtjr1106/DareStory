package com.example.darestory.ui.main.my.readOrOwnBook.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemMyOwnBookBinding
import com.example.darestory.ui.main.my.readOrOwnBook.adapter.MyReadOrOwnBookAdapter
import com.example.domain.model.vo.MyBookVo

class MyOwnBookViewHolder(
    private val binding: ItemMyOwnBookBinding,
    private val listener : MyReadOrOwnBookAdapter.MyReadOrOwnBookDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var myBookVo: MyBookVo
    init {
        binding.constraintLayoutMyOwnBook.setOnClickListener {
            listener.onClickMyBook(myBookVo)
        }
    }

    fun bind(item : MyBookVo) {
        myBookVo = item
        binding.apply {
            textTitle.text = item.myBookTitle
            textExplain.text = item.myBookDescription
        }
    }
}