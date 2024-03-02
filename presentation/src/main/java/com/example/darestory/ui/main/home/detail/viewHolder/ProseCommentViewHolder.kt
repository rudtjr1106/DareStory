package com.example.darestory.ui.main.home.detail.viewHolder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemDetailProseCommentBinding
import com.example.darestory.ui.main.home.detail.adapter.DetailPageAdapter
import com.example.darestory.util.TimeFormatter
import com.example.domain.model.vo.CommentVo

class ProseCommentViewHolder(
    private val binding: ItemDetailProseCommentBinding,
    private val listener : DetailPageAdapter.DetailPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var commentVo : CommentVo
    init {
        binding.imageBtnCommentMenu.setOnClickListener {
            listener.onClickCommentMenu(commentVo.commentId, commentVo.writer)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item : CommentVo) {
        commentVo = item
        binding.apply {
            textWriter.text = item.writer
            textCreatedAt.text = if(item.date.isNotEmpty()) TimeFormatter.getAgoTime(item.date) else item.date
            textCommentContent.text = item.content.replace("\\n", "\n")
        }
    }
}