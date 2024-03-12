package com.darestory.presentation.ui.common.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.R
import com.darestory.presentation.databinding.ItemBottomSheetBinding
import com.darestory.presentation.ui.common.adapter.BottomSheetMenuAdapter
import com.darestory.domain.model.enums.BottomSheetMenuItemType

class BottomSheetMenuItemViewHolder(
    private val binding: ItemBottomSheetBinding,
    private val listener: BottomSheetMenuAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            menuType?.let {
                listener.onClickMenu(it)
            }
        }
    }

    private var menuType:BottomSheetMenuItemType? = null

    fun bind(item: BottomSheetMenuItemType) {
        menuType = item
        binding.apply {
            imageViewIcon.setImageResource(getMenuIconRes(item))
            textViewContent.text = root.context.getString(getMenuContentStringRes(item))
        }
    }

    private fun getMenuIconRes(menuType: BottomSheetMenuItemType):Int {
        return when(menuType) {
            BottomSheetMenuItemType.DISCUSSION_EDIT,
            BottomSheetMenuItemType.PROSE_EDIT -> R.drawable.ic_edit_white

            BottomSheetMenuItemType.DISCUSSION_DELETE,
            BottomSheetMenuItemType.COMMENT_DELETE,
            BottomSheetMenuItemType.PROSE_DELETE -> R.drawable.ic_delete_empty_white

            BottomSheetMenuItemType.REPORT -> R.drawable.ic_report_empty_white

            BottomSheetMenuItemType.PROSE_BOOKMARK -> R.drawable.ic_bookmark_empty_white
        }
    }

    private fun getMenuContentStringRes(menuType: BottomSheetMenuItemType):Int {
        return when(menuType) {
            BottomSheetMenuItemType.DISCUSSION_EDIT,
            BottomSheetMenuItemType.PROSE_EDIT -> R.string.bottom_sheet_edit

            BottomSheetMenuItemType.DISCUSSION_DELETE,
            BottomSheetMenuItemType.COMMENT_DELETE,
            BottomSheetMenuItemType.PROSE_DELETE -> R.string.bottom_sheet_delete

            BottomSheetMenuItemType.REPORT -> R.string.bottom_sheet_report

            BottomSheetMenuItemType.PROSE_BOOKMARK ->R.string.bottom_sheet_bookmark
        }
    }
}