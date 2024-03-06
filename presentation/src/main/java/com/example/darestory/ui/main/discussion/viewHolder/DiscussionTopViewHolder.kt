package com.example.darestory.ui.main.discussion.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemAllProseBinding
import com.example.darestory.databinding.ItemLayoutDiscussionTopBinding
import com.example.darestory.ui.main.discussion.adapter.DiscussionAdapter
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.util.UserInfo
import com.example.domain.model.enums.SortType
import com.example.domain.model.vo.DiscussionVo
import com.example.domain.model.vo.ProseVo

class DiscussionTopViewHolder(
    private val binding: ItemLayoutDiscussionTopBinding,
    private val listener: DiscussionAdapter.DiscussionDelegate
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.apply {
            textBtnSortAge.text = UserInfo.info.age

            imageBtnNewWrite.setOnClickListener {
                listener.onClickWriteDiscussion()
            }

            imageBtnSearch.setOnClickListener {
                listener.onClickSearch()
            }

            textBtnSortPopular.setOnClickListener {
                onClickTextBtnSortPopular()
                returnTextBtnSortRecent()
                returnTextBtnSortAge()
                listener.onClickSort(SortType.POPULAR)
            }
            textBtnSortRecent.setOnClickListener {
                onClickTextBtnSortRecent()
                returnTextBtnSortPopular()
                returnTextBtnSortAge()
                listener.onClickSort(SortType.RECENT)
            }
            textBtnSortAge.setOnClickListener {
                onClickTextBtnSortAge()
                returnTextBtnSortPopular()
                returnTextBtnSortRecent()
                listener.onClickSort(SortType.AGE)
            }
        }
    }

    fun bind() {

    }

    private fun onClickTextBtnSortPopular(){
        binding.apply {
            textBtnSortPopular.setTextColor(ContextCompat.getColor(root.context, R.color.purple_600))
            textBtnSortPopular.setTextAppearance(R.style.title2)
            imageSortPopularDoubleLine.visibility = View.VISIBLE
        }
    }

    private fun onClickTextBtnSortRecent(){
        binding.apply {
            textBtnSortRecent.setTextColor(ContextCompat.getColor(root.context, R.color.purple_600))
            textBtnSortRecent.setTextAppearance(R.style.title2)
            imageSortRecentDoubleLine.visibility = View.VISIBLE
        }
    }

    private fun onClickTextBtnSortAge(){
        binding.apply {
            textBtnSortAge.setTextColor(ContextCompat.getColor(root.context, R.color.purple_600))
            textBtnSortAge.setTextAppearance(R.style.title2)
            imageSortAgeDoubleLine.visibility = View.VISIBLE
        }
    }

    private fun returnTextBtnSortPopular(){
        binding.apply {
            textBtnSortPopular.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
            textBtnSortPopular.setTextAppearance(R.style.body2)
            imageSortPopularDoubleLine.visibility = View.INVISIBLE
        }
    }

    private fun returnTextBtnSortRecent(){
        binding.apply {
            textBtnSortRecent.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
            textBtnSortRecent.setTextAppearance(R.style.body2)
            imageSortRecentDoubleLine.visibility = View.INVISIBLE
        }
    }

    private fun returnTextBtnSortAge(){
        binding.apply {
            textBtnSortAge.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
            textBtnSortAge.setTextAppearance(R.style.body2)
            imageSortAgeDoubleLine.visibility = View.INVISIBLE
        }
    }
}