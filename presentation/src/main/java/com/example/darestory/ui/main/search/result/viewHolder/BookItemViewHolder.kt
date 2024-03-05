package com.example.darestory.ui.main.search.result.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.darestory.R
import com.example.darestory.databinding.ItemDiscussionBookListBinding
import com.example.darestory.ui.main.search.result.adapter.ResultSearchAdapter
import com.example.darestory.util.TimeFormatter
import com.example.domain.model.vo.BookVo

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