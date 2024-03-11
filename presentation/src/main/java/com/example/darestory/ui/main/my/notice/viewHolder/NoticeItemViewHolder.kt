package com.example.darestory.ui.main.my.notice.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemMyOwnBookBinding
import com.example.darestory.ui.main.my.notice.adapter.NoticeAdapter
import com.example.domain.model.vo.NoticeVo

class NoticeItemViewHolder(
    private val binding: ItemMyOwnBookBinding,
    private val listener : NoticeAdapter.NoticeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var noticeVo: NoticeVo
    init {
        binding.constraintLayoutMyOwnBook.setOnClickListener {
            listener.onClickNotice(noticeVo.noticeId)
        }
    }

    fun bind(item : NoticeVo) {
        noticeVo = item
        binding.apply {
            imageBtnNext.visibility = View.INVISIBLE
            textTitle.text = item.title
            textExplain.text = item.content.replace("\\n", "\n")
        }
    }
}