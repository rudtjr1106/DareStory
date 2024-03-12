package com.darestory.presentation.ui.main.my.writing.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.databinding.ItemMyOwnBookProseBinding
import com.darestory.presentation.ui.main.my.writing.adapter.MyProseAndDiscussionAdapter
import com.darestory.domain.model.vo.ProseVo

class MyOwnBookProseViewHolder(
    private val binding: ItemMyOwnBookProseBinding,
    private val listener: MyProseAndDiscussionAdapter.MyProseAndDiscussionDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var proseItem : ProseVo

    init {
        binding.apply {
            imageBtnMenu.setOnClickListener {
                listener.onClickMenu(proseItem)
            }

            constraintLayoutProse.setOnClickListener {
                listener.onClickProse(proseItem.proseId)
            }
        }
    }

    fun bind(item : ProseVo) {
        proseItem = item
        binding.apply {
            textTitle.text = item.title
            textAuthor.text = item.author

        }
    }
}