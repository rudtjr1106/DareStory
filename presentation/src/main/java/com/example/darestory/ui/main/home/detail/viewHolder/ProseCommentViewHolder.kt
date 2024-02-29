package com.example.darestory.ui.main.home.detail.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemDetailProseCommentBinding
import com.example.domain.model.vo.CommentVo

class ProseCommentViewHolder(
    private val binding: ItemDetailProseCommentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : CommentVo) {
        binding.apply {
            textWriter.text = item.writer
            textCreatedAt.text = item.date
            textCommentContent.text = item.content.replace("\\n", "\n")
        }
    }
}