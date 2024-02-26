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
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.darestory.R
import com.example.darestory.databinding.InputDialogBinding
import com.example.darestory.util.DareLog
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class InputDialog @Inject constructor(@ActivityContext private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: InputDialogBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.input_dialog, null, false)
    }

    private var dialog: AlertDialog? = null

    fun setTitle(@StringRes messageId: Int): InputDialog {
        binding.textTitle.apply {
            text = context.getText(messageId)
        }
        return this
    }

    fun setTitle(message: CharSequence): InputDialog {
        binding.textTitle.apply {
            text = message
        }
        return this
    }

    fun setPositiveButton(@StringRes textId: Int, onClickListener: (view: View) -> (Unit)): InputDialog {
        binding.btnYes.apply {
            text = context.getText(textId)
            setOnClickListener(onClickListener)
            dismiss()
        }
        return this
    }

    fun setPositiveButton(text: CharSequence, onClickListener: (view: View) -> (Unit)): InputDialog {
        binding.btnYes.apply {
            this.text = text
            setOnClickListener(onClickListener)
            dismiss()
        }
        return this
    }

    fun setNegativeButton(@StringRes textId: Int, onClickListener: (view: View) -> (Unit)): InputDialog {
        binding.btnNo.apply {
            visibility = View.VISIBLE
            text = context.getText(textId)
            this.text = text
            setOnClickListener(onClickListener)
        }
        return this
    }

    fun setNegativeButton(text: CharSequence, onClickListener: (view: View) -> (Unit)): InputDialog {
        binding.btnNo.apply {
            visibility = View.VISIBLE
            this.text = text
            setOnClickListener(onClickListener)
        }
        return this
    }

    fun show() {
        bindListener()
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.show()
    }

    fun dismiss() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.removeView(binding.root)
        dialog?.dismiss()
    }

    private fun bindListener() {
        binding.apply {
            editTextInput.doAfterTextChanged {
                checkIsEmptyEditText()
            }
            imageBtnResetEditText.setOnClickListener {
                editTextInput.setText("")
                checkIsEmptyEditText()
            }
        }
    }

    private fun checkIsEmptyEditText() {
        binding.apply {
            if (editTextInput.text.isNotEmpty()) {
                imageBtnResetEditText.visibility = View.VISIBLE
                imageLineEditText.setBackgroundResource(R.drawable.img_line_purple_dark_600)
            } else {
                imageBtnResetEditText.visibility = View.GONE
                imageLineEditText.setBackgroundResource(R.drawable.img_line_gray_600)
            }
        }
    }

    fun getEditText() : String {
        return binding.editTextInput.text.toString()
    }
}