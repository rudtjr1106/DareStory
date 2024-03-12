package com.darestory.presentation.ui.main.discussion.replyComment.adapter.viewHolder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.R
import com.darestory.presentation.databinding.ItemDetailDiscussionCommentBinding
import com.darestory.presentation.ui.main.discussion.replyComment.adapter.DiscussionCommentAdapter
import com.darestory.presentation.util.TimeFormatter
import com.darestory.domain.model.enums.CommentType
import com.darestory.domain.model.vo.DisCommentVo

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