package com.darestory.presentation.ui.main.home.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.darestory.presentation.R
import com.darestory.presentation.databinding.ItemLayoutHomeTodayProseBinding
import com.darestory.presentation.ui.main.home.adapter.HomeAdapter
import com.darestory.presentation.ui.main.home.adapter.TodayProseViewPagerAdapter
import com.darestory.presentation.util.UserInfo
import com.darestory.presentation.util.px
import com.darestory.domain.model.enums.SortType
import com.darestory.domain.model.vo.ProseVo
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class HomeTodayProseViewHolder (
    private val binding : ItemLayoutHomeTodayProseBinding,
    private val listener: HomeAdapter.HomeDelegate,
    private val sortType: SortType
) : RecyclerView.ViewHolder(binding.root){

    companion object{
        const val MARGIN_HORIZONTAL = 70
        const val MAX_ITEM = 5
        const val INDICATOR_WIDTH = 24
        const val INDICATOR_HEIGHT = 4
    }

    private val todayProseViewPagerAdapter = TodayProseViewPagerAdapter(listener)

    init {
        binding.apply {
            imageBtnSearch.setOnClickListener {
                listener.onClickSearch()
            }
            imageBtnNewWrite.setOnClickListener {
                listener.onClickWriteProse()
            }
            initTextButton()
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
        setIndicator()
    }

    private fun initTextButton(){
        when(sortType){
            SortType.POPULAR -> {
                onClickTextBtnSortPopular()
                returnTextBtnSortRecent()
                returnTextBtnSortAge()
            }
            SortType.RECENT -> {
                onClickTextBtnSortRecent()
                returnTextBtnSortPopular()
                returnTextBtnSortAge()
            }
            SortType.AGE -> {
                onClickTextBtnSortAge()
                returnTextBtnSortPopular()
                returnTextBtnSortRecent()
            }
        }
    }

    private fun bindSortTextButton(){
        binding.apply {
            textBtnSortAge.text = UserInfo.info.age
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

    private fun setIndicator(){
        binding.apply {
            indicatorView.apply {
                setSliderColor(ContextCompat.getColor(root.context, R.color.gray_600), ContextCompat.getColor(root.context, R.color.gray_100))
                setSliderWidth(INDICATOR_WIDTH.px.toFloat())
                setSliderHeight(INDICATOR_HEIGHT.px.toFloat())
                setSlideMode(IndicatorSlideMode.SMOOTH)
                setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                setPageSize(viewPagerTodayProse.adapter!!.itemCount)
                notifyDataChanged()
            }

            viewPagerTodayProse.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    indicatorView.onPageSelected(position)
                }
            })
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