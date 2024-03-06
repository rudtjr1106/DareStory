package com.example.darestory.ui.main.home.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.util.UserInfo
import com.example.domain.model.vo.ProseVo

class HomeNormalProseViewHolder(
    private val binding: ItemAllProseBinding,
    private val listener: HomeAdapter.HomeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var proseItem : ProseVo

    init {
        binding.apply {
            constraintLayoutProse.setOnClickListener {
                listener.onClickProse(proseItem.proseId)
            }
        }
    }

    fun bind(item : ProseVo) {
        proseItem = item
        binding.apply {
            val likeImage = if(item.whoLiked.containsValue(UserInfo.info.nickName)) R.drawable.ic_like_filled_purple_600
            else R.drawable.ic_like_gray_600
            imageLike.setImageResource(likeImage)
            textTitle.text = item.title
            textAuthor.text = item.author
            textLikeNum.text = item.likeCount.toString()
            textCommentNum.text = item.commentCount.toString()

        }
    }
}