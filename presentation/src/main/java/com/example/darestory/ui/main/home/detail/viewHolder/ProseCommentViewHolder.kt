package com.example.darestory.ui.main.home.detail.viewHolder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemDetailProseCommentBinding
import com.example.darestory.util.TimeFormatter
import com.example.domain.model.vo.CommentVo

class ProseCommentViewHolder(
    private val binding: ItemDetailProseCommentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item : CommentVo) {
        binding.apply {
            textWriter.text = item.writer
            textCreatedAt.text = if(item.date.isNotEmpty()) TimeFormatter.getAgoTime(item.date) else item.date
            textCommentContent.text = item.content.replace("\\n", "\n")
        }
    }
}