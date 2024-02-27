package com.example.darestory.ui.main.home.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.darestory.databinding.ItemLayoutHomeTodayProseBinding
import com.example.darestory.ui.main.home.adapter.TodayProseViewPagerAdapter
import com.example.darestory.util.px

class HomeTodayProseViewHolder (
    private val binding : ItemLayoutHomeTodayProseBinding,
) : RecyclerView.ViewHolder(binding.root){

    private val todayProseViewPagerAdapter = TodayProseViewPagerAdapter()

    companion object{
        const val ITEM_SPAN = 4
        const val VERTICAL_SPACE = 10
    }
//
//    private val homeAdapter : HomeCategoryAdapter by lazy {
//        HomeCategoryAdapter(listener)
//    }

    init {
//        binding.recyclerViewCategory.apply {
//            layoutManager = GridLayoutManager(context, ITEM_SPAN)
//            addItemDecoration(GridSpaceDecoration(ITEM_SPAN, 8.px, VERTICAL_SPACE.px, false))
//            adapter = homeAdapter
//        }
    }


    fun bind() {
        binding.apply {
            viewPagerTodayProse.apply {
                adapter = todayProseViewPagerAdapter
                offscreenPageLimit = 1
            }

            todayProseViewPagerAdapter.submitList(listOf("1", "2","3"))
        }
        //homeAdapter.submitList(item)
    }
}