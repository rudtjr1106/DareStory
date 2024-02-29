package com.example.darestory.ui.main.home.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemTodayProseBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.domain.model.vo.ProseVo

class TodayProseViewPagerViewHolder(
    private val binding: ItemTodayProseBinding,
    private val listener: HomeAdapter.HomeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var proseItem : ProseVo

    init {
        binding.apply {
            constraintLayoutTodayProse.setOnClickListener {
                listener.onClickProse(proseItem.proseId)
            }
        }
    }

    fun bind(item : ProseVo) {
        proseItem = item
        binding.apply {
            textTitle.text = item.title
            textContent.text = item.content
            textAuthor.text = item.author
            textCommentNum.text = item.commentCount.toString()
            textLikeNum.text = item.likeCount.toString()
        }
    }
}