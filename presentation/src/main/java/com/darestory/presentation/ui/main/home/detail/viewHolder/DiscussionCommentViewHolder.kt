package com.darestory.presentation.ui.main.home.detail.viewHolder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.R
import com.darestory.presentation.databinding.ItemDetailDiscussionCommentBinding
import com.darestory.presentation.ui.main.home.detail.adapter.DetailPageAdapter
import com.darestory.presentation.util.TimeFormatter
import com.darestory.domain.model.vo.DisCommentVo

class DiscussionCommentViewHolder(
    private val binding: ItemDetailDiscussionCommentBinding,
    private val listener : DetailPageAdapter.DetailPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var disCommentVo: DisCommentVo
    init {
        binding.imageBtnCommentMenu.setOnClickListener {
            listener.onClickCommentMenu(disCommentVo.commentId, disCommentVo.writer)
        }

        binding.textCommentCount.setOnClickListener {
            listener.onClickReplyComment(disCommentVo)
        }

        binding.textBtnAddReply.setOnClickListener {
            listener.onClickReplyComment(disCommentVo)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item : DisCommentVo) {
        disCommentVo = item
        binding.apply {
            textWriter.text = item.writer
            textCreatedAt.text = if(item.date.isNotEmpty()) TimeFormatter.getAgoTime(item.date) else item.date
            textCommentContent.text = item.content.replace("\\n", "\n")
            textCommentCount.text = root.resources.getString(R.string.discussion_comment_count, item.replyComment.size)
        }
    }
}