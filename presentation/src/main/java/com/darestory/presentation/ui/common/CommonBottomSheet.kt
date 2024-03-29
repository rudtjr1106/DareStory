package com.darestory.presentation.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.darestory.presentation.databinding.CommonBottomSheetBinding
import com.darestory.presentation.ui.common.adapter.BottomSheetMenuAdapter
import com.darestory.presentation.util.serializable
import com.darestory.domain.model.enums.BottomSheetMenuItemType
import com.darestory.domain.model.enums.BottomSheetType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommonBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val KEY_MENU_TYPE = "KEY_MENU_TYPE"
        private const val KEY_SELECTED_MENU_TYPE = "KEY_SELECTED_MENU_TYPE"

        fun newInstance(
            menuType: BottomSheetType,
            selectedMenuType: BottomSheetMenuItemType? = null,
            menuClick: (BottomSheetMenuItemType) -> Unit
        ) = CommonBottomSheet().apply {
            this.menuClick = menuClick
            arguments = Bundle().apply {
                putSerializable(KEY_MENU_TYPE, menuType)
                putSerializable(KEY_SELECTED_MENU_TYPE, selectedMenuType)
            }
        }
    }

    private var menuClick: ((BottomSheetMenuItemType) -> Unit)? = null
    private val menuType: BottomSheetType by lazy {
        arguments?.serializable(KEY_MENU_TYPE) ?: BottomSheetType.PROSE_NORMAL
    }
    private val selectedMenuType: BottomSheetMenuItemType? by lazy {
        arguments?.serializable(KEY_SELECTED_MENU_TYPE)
    }

    private val binding: CommonBottomSheetBinding by lazy {
        CommonBottomSheetBinding.inflate(layoutInflater)
    }

    private val listAdapter: BottomSheetMenuAdapter by lazy {
        BottomSheetMenuAdapter(object : BottomSheetMenuAdapter.Delegate {
            override fun onClickMenu(type: BottomSheetMenuItemType) {
                dismiss()
                menuClick?.invoke(type)
            }

            override val selectedDialogMenuItem: BottomSheetMenuItemType? = selectedMenuType
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.behavior.apply {
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = listAdapter
            }
        }

        listAdapter.submitList(getMenuList())
    }

    private fun getMenuList(): List<BottomSheetMenuItemType> {
        return when (menuType) {
            BottomSheetType.PROSE_AUTHOR -> listOf(
                BottomSheetMenuItemType.PROSE_EDIT,
                BottomSheetMenuItemType.PROSE_DELETE
            )

            BottomSheetType.PROSE_NORMAL -> listOf(
                BottomSheetMenuItemType.PROSE_BOOKMARK,
                BottomSheetMenuItemType.REPORT
            )

            BottomSheetType.COMMENT_WRITER -> listOf(
                BottomSheetMenuItemType.COMMENT_DELETE,
            )

            BottomSheetType.COMMENT_NORMAL -> listOf(
                BottomSheetMenuItemType.REPORT
            )

            BottomSheetType.DISCUSSION_AUTHOR -> listOf(
                BottomSheetMenuItemType.DISCUSSION_EDIT,
                BottomSheetMenuItemType.DISCUSSION_DELETE
            )
            BottomSheetType.DISCUSSION_NORMAL -> listOf(
                BottomSheetMenuItemType.REPORT
            )
        }
    }
}
