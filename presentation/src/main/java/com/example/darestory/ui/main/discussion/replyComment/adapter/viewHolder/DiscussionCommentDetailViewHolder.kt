package com.example.darestory.ui.main.discussion.replyComment.adapter.viewHolder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemDetailDiscussionCommentBinding
import com.example.darestory.ui.main.discussion.replyComment.adapter.DiscussionCommentAdapter
import com.example.darestory.util.TimeFormatter
import com.example.domain.model.enums.CommentType
import com.example.domain.model.vo.CommentVo
import com.example.domain.model.vo.DisCommentVo

class DiscussionCommentDetailViewHolder(
    private val binding: ItemDetailDiscussionCommentBinding,
    private val listener : DiscussionCommentAdapter.DiscussionCommentDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var disCommentVo: DisCommentVo
    init {
        binding.imageBtnCommentMenu.setOnClickListener {
            listener.onClickCommentMenu(disCommentVo.commentId, disCommentVo.writer, CommentType.NORMAL)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item : DisCommentVo) {
        disCommentVo = item
        binding.apply {
            textWriter.setTextColor(ContextCompat.getColor(root.context, R.color.dark_purple_600))
            textWriter.text = item.writer
            textCreatedAt.text = if(item.date.isNotEmpty()) TimeFormatter.getAgoTime(item.date) else item.date
            textCommentContent.text = item.content.replace("\\n", "\n")
            textCommentCount.text = root.resources.getString(R.string.discussion_comment_count, item.replyComment.size)
        }
    }
}