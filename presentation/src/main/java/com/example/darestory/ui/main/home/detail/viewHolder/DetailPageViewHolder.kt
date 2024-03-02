package com.example.darestory.ui.main.home.detail.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemLayoutDetailBinding
import com.example.darestory.ui.main.home.detail.adapter.DetailPageAdapter
import com.example.domain.model.enums.DetailType
import com.example.domain.model.vo.DetailContentVo
import com.example.domain.model.vo.ProseVo

class DetailPageViewHolder(
    private val binding: ItemLayoutDetailBinding,
    private val listener : DetailPageAdapter.DetailPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var content : DetailContentVo

    init {
        binding.apply {
            imageBtnLike.setOnClickListener {
                listener.onClickLike(content.pageId, content.isLiked)
            }

            imageBtnBack.setOnClickListener {
                listener.onClickBack()
            }

            imageBtnMenu.setOnClickListener {
                listener.onClickMenu()
            }
        }
    }

    fun bind(item : DetailContentVo) {
        content = item
        binding.apply {
            textTitle.text = content.title.replace("\\n", "\n")
            textAuthor.text = binding.root.resources.getString(R.string.home_prose_main_author, content.author)
            textCreatedDate.text = content.createdAt.split("/")[0]
            textContent.text = content.content.replace("\\n", "\n")
            val likeImage = if(content.isLiked) R.drawable.ic_like_filled_purple_600 else R.drawable.ic_like_empty_gray_400
            imageBtnLike.setBackgroundResource(likeImage)
            textLikeNum.text = content.likeCount.toString()
            textCommentNum.text = content.commentCount.toString()
        }
    }
}