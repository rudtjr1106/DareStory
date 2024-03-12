package com.darestory.presentation.ui.main.home.detail.viewHolder

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.R
import com.darestory.presentation.databinding.ItemDetailProseCommentBinding
import com.darestory.domain.model.vo.CommentVo

class ProseAuthorCommentViewHolder(
    private val binding: ItemDetailProseCommentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.textWriter.setTextColor(ContextCompat.getColor(binding.root.context, R.color.dark_purple_600))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item : CommentVo) {
        binding.apply {
            imageBtnCommentMenu.visibility = View.INVISIBLE
            textWriter.text = item.writer
            textCommentContent.text = item.content.replace("\\n", "\n")
        }
    }
}