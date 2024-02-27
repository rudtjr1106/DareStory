package com.example.darestory.ui.main.home.viewHolder

import com.example.darestory.databinding.ItemLayoutHomeAllProseBinding
import androidx.recyclerview.widget.RecyclerView

class HomeAllProseViewHolder (
    private val binding : ItemLayoutHomeAllProseBinding,
) : RecyclerView.ViewHolder(binding.root){

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
        //homeAdapter.submitList(item)
    }
}