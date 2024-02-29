package com.example.darestory.ui.main.home.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.R
import com.example.darestory.databinding.ItemLayoutHomeTodayProseBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.home.adapter.TodayProseViewPagerAdapter
import com.example.darestory.util.px
import com.example.domain.model.vo.ProseVo

class HomeTodayProseViewHolder (
    private val binding : ItemLayoutHomeTodayProseBinding,
    private val listener: HomeAdapter.HomeDelegate
) : RecyclerView.ViewHolder(binding.root){

    companion object{
        const val MARGIN_HORIZONTAL = 70
        const val MAX_ITEM = 3
    }

    private val todayProseViewPagerAdapter = TodayProseViewPagerAdapter(listener)

    init {
        binding.apply {
            bindSortTextButton()
            setIndicator()
        }
    }


    fun bind(item : List<ProseVo>) {
        todayProseViewPagerAdapter.submitList(item)
        binding.apply {
            viewPagerTodayProse.apply {
                adapter = todayProseViewPagerAdapter
                children.forEach { child ->
                    if (child is RecyclerView) {
                        child.apply {
                            setPadding(MARGIN_HORIZONTAL.px, 0, MARGIN_HORIZONTAL.px, 0)
                            clipToPadding = false
                        }
                        return@forEach
                    }
                }
                setPageTransformer { page, position ->
                    val absPosition = Math.abs(position)
                    val scale = 0.8f + (0.2f * (1 - absPosition))
                    page.scaleX = scale
                    page.scaleY = scale
                    page.alpha = (1.5 - absPosition).toFloat()
                }
                offscreenPageLimit = MAX_ITEM.toInt()
            }
        }
    }

    private fun bindSortTextButton(){
        binding.apply {
            textBtnSortPopular.setOnClickListener {
                onClickTextBtnSortPopular()
                returnTextBtnSortRecent()
                returnTextBtnSortAge()
                listener.onClickSortPopular()
            }
            textBtnSortRecent.setOnClickListener {
                onClickTextBtnSortRecent()
                returnTextBtnSortPopular()
                returnTextBtnSortAge()
                listener.onClickSortRecent()
            }
            textBtnSortAge.setOnClickListener {
                onClickTextBtnSortAge()
                returnTextBtnSortPopular()
                returnTextBtnSortRecent()
                listener.onClickSortAge()
            }
        }
    }

    private fun setIndicator(){
        binding.apply {
            //TODO 여기 인디케이터 안댐
        }
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