package com.example.darestory.ui.main.discussion.viewHolder

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemDiscussionBookListBinding
import com.example.darestory.databinding.ItemTodayProseBinding
import com.example.darestory.ui.main.discussion.adapter.DiscussionAdapter
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo

class DiscussionItemViewHolder(
    private val binding: ItemDiscussionBookListBinding,
    private val listener: DiscussionAdapter.DiscussionDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var disItem : DiscussionVo
    init {
        binding.apply {
            constraintLayoutDiscussionItem.setOnClickListener {
                listener.onClickDiscussion(disItem.discussionId)
            }
        }
    }

    fun bind(item : DiscussionVo) {
        disItem = item
        binding.apply {
            imageBook.setImageURI(item.bookImage.toUri())
            textBookTitle.text = item.bookTitle
            textDiscussionTitle.text = item.title
            textCommentNum.text = item.commentCount.toString()
            textLikeNum.text = item.likeCount.toString()
        }
    }
}