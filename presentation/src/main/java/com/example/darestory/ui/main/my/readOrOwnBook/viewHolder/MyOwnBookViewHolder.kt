package com.example.darestory.ui.main.my.readOrOwnBook.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.darestory.R
import com.example.darestory.databinding.ItemDiscussionBookListBinding
import com.example.darestory.databinding.ItemMyOwnBookBinding
import com.example.darestory.ui.main.my.readOrOwnBook.adapter.MyReadOrOwnBookAdapter
import com.example.darestory.ui.main.search.result.adapter.ResultSearchAdapter
import com.example.darestory.util.TimeFormatter
import com.example.domain.model.vo.BookVo
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