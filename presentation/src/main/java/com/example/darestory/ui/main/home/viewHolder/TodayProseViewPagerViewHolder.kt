package com.example.darestory.ui.main.home.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemTodayProseBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.domain.model.vo.ProseVo

class TodayProseViewPagerViewHolder(
    private val binding: ItemTodayProseBinding,
    private val listener: HomeAdapter.HomeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var proseItem : ProseVo
    companion object{
        const val DEVELOP_PROSE_ID = -100
    }

    init {
        binding.apply {
            constraintLayoutTodayProse.setOnClickListener {
                if(proseItem.proseId != DEVELOP_PROSE_ID) listener.onClickProse(proseItem.proseId)
            }
        }
    }

    fun bind(item : ProseVo) {
        proseItem = item
        binding.apply {
            textTitle.text = item.title.replace("\\n", "\n")
            textContent.text = item.content.replace("\\n", "\n")
            textAuthor.text = item.author
            textCommentNum.text = item.commentCount.toString()
            textLikeNum.text = item.likeCount.toString()
        }
    }
}