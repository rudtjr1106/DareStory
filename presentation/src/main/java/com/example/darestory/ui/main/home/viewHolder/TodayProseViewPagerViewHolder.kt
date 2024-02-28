package com.example.darestory.ui.main.home.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemTodayProseBinding
import com.example.domain.model.vo.ProseVo

class TodayProseViewPagerViewHolder(
    private val binding: ItemTodayProseBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : ProseVo) {
        binding.apply {
            textTitle.text = item.title
            textContent.text = item.content
            textAuthor.text = item.author
            textCommentNum.text = item.commentCount.toString()
            textLikeNum.text = item.likeCount.toString()
        }
    }
}