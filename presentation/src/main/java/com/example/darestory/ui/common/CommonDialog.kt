package com.example.darestory.ui.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.darestory.R
import com.example.darestory.databinding.CommonDialogBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CommonDialog@Inject constructor(@ActivityContext private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: CommonDialogBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.common_dialog, null, false)
    }

    private var dialog: AlertDialog? = null

    fun setTitle(@StringRes messageId: Int): CommonDialog {
        binding.textTitle.apply {
            text = context.getText(messageId)
        }
        return this
    }

    fun setTitle(message: CharSequence): CommonDialog {
        binding.textTitle.apply {
            text = message
        }
        return this
    }

    fun showIcon() : CommonDialog {
        binding.textTitle.visibility = View.GONE
        binding.imageCheck.visibility = View.VISIBLE
        return this
    }

    fun setDescription(@StringRes messageId: Int?): CommonDialog {
        binding.textContent.apply {
            visibility = View.VISIBLE
            text = messageId?.let { context.getText(it) }
        }
        return this
    }

    fun setDescription(message: CharSequence): CommonDialog {
        binding.textContent.apply {
            text = message
        }
        return this
    }

    fun setPositiveButton(@StringRes textId: Int, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.btnYes.apply {
            text = context.getText(textId)
            setOnClickListener(onClickListener)
            dismiss()
        }
        return this
    }

    fun setPositiveButton(text: CharSequence, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.btnYes.apply {
            this.text = text
            setOnClickListener(onClickListener)
            dismiss()
        }
        return this
    }

    fun setNegativeButton(@StringRes textId: Int, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.btnNo.apply {
            visibility = View.VISIBLE
            text = context.getText(textId)
            this.text = text
            setOnClickListener(onClickListener)
        }
        return this
    }

    fun setNegativeButton(text: CharSequence, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.btnNo.apply {
            visibility = View.VISIBLE
            this.text = text
            setOnClickListener(onClickListener)
        }
        return this
    }

    fun show() {
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun dismiss() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.removeView(binding.root)
        dialog?.dismiss()
    }
}