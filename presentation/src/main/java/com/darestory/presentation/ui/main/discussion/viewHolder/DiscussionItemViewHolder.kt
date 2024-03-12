package com.darestory.presentation.ui.main.discussion.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darestory.presentation.R
import com.darestory.presentation.databinding.ItemDiscussionBookListBinding
import com.darestory.presentation.ui.main.discussion.adapter.DiscussionAdapter
import com.darestory.presentation.util.UserInfo
import com.darestory.domain.model.vo.DiscussionVo

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
            Glide.with(root.context)
                .load(item.bookImage)
                .into(imageBook)
            val likeImage = if(item.whoLiked.containsValue(UserInfo.info.nickName)) R.drawable.ic_like_filled_purple_600
                        else R.drawable.ic_like_gray_600
            imageLike.setImageResource(likeImage)
            textBookTitle.text = item.bookTitle
            textDiscussionTitle.text = item.title
            textCommentNum.text = item.commentCount.toString()
            textLikeNum.text = item.likeCount.toString()
        }
    }
}