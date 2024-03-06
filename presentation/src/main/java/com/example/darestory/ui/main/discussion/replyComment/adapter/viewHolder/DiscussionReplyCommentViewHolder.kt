package com.example.darestory.ui.main.discussion.replyComment.adapter.viewHolder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemDetailDiscussionReplyCommentBinding
import com.example.darestory.ui.main.discussion.replyComment.adapter.DiscussionCommentAdapter
import com.example.darestory.util.TimeFormatter
import com.example.domain.model.enums.CommentType
import com.example.domain.model.vo.DisCommentVo

class DiscussionReplyCommentViewHolder(
    private val binding: ItemDetailDiscussionReplyCommentBinding,
    private val listener : DiscussionCommentAdapter.DiscussionCommentDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var disCommentVo: DisCommentVo
    init {
        binding.imageBtnCommentMenu.setOnClickListener {
            listener.onClickCommentMenu(disCommentVo.commentId, disCommentVo.writer, CommentType.REPLY)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item : DisCommentVo) {
        disCommentVo = item
        binding.apply {
            textWriter.text = item.writer
            textCreatedAt.text = if(item.date.isNotEmpty()) TimeFormatter.getAgoTime(item.date) else item.date
            textCommentContent.text = item.content.replace("\\n", "\n")
        }
    }
}