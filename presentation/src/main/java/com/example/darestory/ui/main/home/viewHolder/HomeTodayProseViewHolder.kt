package com.example.darestory.ui.main.home.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.marginLeft
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.darestory.R
import com.example.darestory.databinding.ItemLayoutHomeTodayProseBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.home.adapter.TodayProseViewPagerAdapter
import com.example.darestory.util.DareLog
import com.example.darestory.util.px
import com.example.domain.model.vo.ProseVo

class HomeTodayProseViewHolder (
    private val binding : ItemLayoutHomeTodayProseBinding,
    private val listener: HomeAdapter.HomeDelegate
) : RecyclerView.ViewHolder(binding.root){

    private val todayProseViewPagerAdapter = TodayProseViewPagerAdapter()


    init {
        binding.apply {
            bindSortTextButton()
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
                            setPadding(53.px, 0, 53.px, 0)
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
                offscreenPageLimit = 3
            }
            if(item.size >= 3){
                viewPagerTodayProse.currentItem = 1
            }
        }
    }

    private fun bindSortTextButton(){
        binding.apply {
            textBtnSortPopular.setOnClickListener {
                textBtnSortPopular.setTextColor(ContextCompat.getColor(root.context, R.color.purple_600))
                imageSortPopularDoubleLine.visibility = View.VISIBLE
                textBtnSortRecent.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
                imageSortRecentDoubleLine.visibility = View.INVISIBLE
                textBtnSortAge.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
                imageSortAgeDoubleLine.visibility = View.INVISIBLE
                listener.onClickSortPopular()
            }
            textBtnSortRecent.setOnClickListener {
                textBtnSortRecent.setTextColor(ContextCompat.getColor(root.context, R.color.purple_600))
                imageSortRecentDoubleLine.visibility = View.VISIBLE
                textBtnSortPopular.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
                imageSortPopularDoubleLine.visibility = View.INVISIBLE
                textBtnSortAge.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
                imageSortAgeDoubleLine.visibility = View.INVISIBLE
                listener.onClickSortRecent()
            }
            textBtnSortAge.setOnClickListener {
                textBtnSortAge.setTextColor(ContextCompat.getColor(root.context, R.color.purple_600))
                imageSortAgeDoubleLine.visibility = View.VISIBLE
                textBtnSortRecent.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
                imageSortRecentDoubleLine.visibility = View.INVISIBLE
                textBtnSortPopular.setTextColor(ContextCompat.getColor(root.context, R.color.gray_600))
                imageSortPopularDoubleLine.visibility = View.INVISIBLE
                listener.onClickSortAge()
            }
        }
    }

}