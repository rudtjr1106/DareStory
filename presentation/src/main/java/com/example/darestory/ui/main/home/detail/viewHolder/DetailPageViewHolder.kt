package com.example.darestory.ui.main.home.detail.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemLayoutDetailBinding
import com.example.domain.model.vo.DetailContentVo
import com.example.domain.model.vo.ProseVo

class DetailPageViewHolder(
    private val binding: ItemLayoutDetailBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : DetailContentVo) {
        binding.apply {
            textTitle.text = item.title.replace("\\n", "\n")
            textAuthor.text = binding.root.resources.getString(R.string.home_prose_main_author, item.author)
            textCreatedDate.text = item.createdAt
            textContent.text = item.content.replace("\\n", "\n")
            textLikeNum.text = item.likeCount.toString()
            textCommentNum.text = item.commentCount.toString()

        }
    }
}