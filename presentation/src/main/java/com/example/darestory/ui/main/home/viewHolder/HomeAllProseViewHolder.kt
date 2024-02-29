package com.example.darestory.ui.main.home.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.domain.model.vo.ProseVo

class HomeAllProseViewHolder(
    private val binding: ItemAllProseBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : ProseVo) {
        binding.apply {
            textTitle.text = item.title
            textAuthor.text = item.author
            textLikeNum.text = item.likeCount.toString()
            textCommentNum.text = item.commentCount.toString()

        }
    }
}