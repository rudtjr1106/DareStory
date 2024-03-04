package com.example.darestory.ui.main.home.detail.viewHolder

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemDetailProseCommentBinding
import com.example.darestory.util.TimeFormatter
import com.example.domain.model.vo.CommentVo

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