package com.example.darestory.ui.common.spinner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.R
import com.example.darestory.databinding.CommonDialogBinding
import com.example.darestory.databinding.SpinnerDialogBinding
import com.example.darestory.util.DareLog
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class SpinnerDialog@Inject constructor(@ActivityContext private val context: Context) {

    private var items: List<String> = listOf()

    private val builder: androidx.appcompat.app.AlertDialog.Builder by lazy {
        androidx.appcompat.app.AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: SpinnerDialogBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_dialog, null, false)
    }

    private var dialog: androidx.appcompat.app.AlertDialog? = null

    private val spinnerAdapter: SpinnerAdapter by lazy {
        SpinnerAdapter(object : SpinnerAdapter.SpinnerDelegate {
            override fun onItemClick(name: String) {
                DareLog.D(name)
                dismiss()
            }
        })
    }

    fun setItems(items: List<String>): SpinnerDialog {
        this.items = items
        return this
    }

    fun show() {
        binding.recyclerItem.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = spinnerAdapter
        }
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        spinnerAdapter.submitList(items)
        dialog?.show()
    }

    private fun dismiss() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.removeView(binding.root)
        dialog?.dismiss()
    }
}