package com.example.darestory.ui.main.home.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.domain.model.vo.ProseVo

class HomeAllProseViewHolder(
    private val binding: ItemAllProseBinding,
    private val listener: HomeAdapter.HomeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var proseItem : ProseVo

    init {
        binding.apply {
            constraintLayoutProse.setOnClickListener {
                listener.onClickProse(proseItem.proseId)
            }
        }
    }

    fun bind(item : ProseVo) {
        proseItem = item
        binding.apply {
            textTitle.text = item.title
            textAuthor.text = item.author
            textLikeNum.text = item.likeCount.toString()
            textCommentNum.text = item.commentCount.toString()

        }
    }
}