package com.darestory.presentation.ui.main.search.result.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darestory.presentation.R
import com.darestory.presentation.databinding.ItemDiscussionBookListBinding
import com.darestory.presentation.ui.main.search.result.adapter.ResultSearchAdapter
import com.darestory.presentation.util.TimeFormatter
import com.darestory.domain.model.vo.BookVo

class BookItemViewHolder(
    private val binding: ItemDiscussionBookListBinding,
    private val listener : ResultSearchAdapter.ResultSearchDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var bookVo: BookVo
    init {
        binding.apply {
            imageLike.visibility = View.GONE
            imageComment.visibility = View.GONE
            textLikeNum.visibility = View.GONE
            textCommentNum.visibility = View.GONE

            constraintLayoutDiscussionItem.setOnClickListener {
                listener.onClickBook(bookVo)
            }
        }
    }

    fun bind(item : BookVo) {
        bookVo = item
        binding.apply {
            textBookTitle.text = item.author
            textBook.setTextColor(ContextCompat.getColor(binding.root.context, R.color.dark_purple_600))
            textBook.text = binding.root.resources.getString(R.string.word_author)
            textDiscussionTitle.text = item.title
            textPubDate.text = TimeFormatter.getDotsDate(item.pubdate)
            Glide.with(root.context)
                .load(item.image)
                .into(imageBook)
            textPubDate.visibility = View.VISIBLE
        }
    }
}