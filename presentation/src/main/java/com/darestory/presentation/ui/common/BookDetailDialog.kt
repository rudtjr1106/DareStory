package com.darestory.presentation.ui.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.darestory.presentation.R
import com.darestory.presentation.databinding.BookDetailDialogBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class BookDetailDialog@Inject constructor(@ActivityContext private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: BookDetailDialogBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.book_detail_dialog, null, false)
    }

    private var dialog: AlertDialog? = null

    fun setImage(url : String) : BookDetailDialog{
        binding.apply {
            Glide.with(root.context)
                .load(url)
                .into(imageBook)
        }
        return this
    }

    fun setTitle(text: String): BookDetailDialog {
        binding.textBookTitle.apply {
            this.text = text
        }
        return this
    }

    fun setAuthor(text: String): BookDetailDialog {
        binding.textAuthorName.apply {
            this.text = text
        }
        return this
    }

    fun setPublisher(text: String) : BookDetailDialog {
        binding.textPublisherContent.apply {
            this.text = text
        }
        return this
    }

    fun setDescription(text: String): BookDetailDialog {
        binding.textDescriptionContent.apply {
            this.movementMethod = ScrollingMovementMethod()
            this.text = text
        }
        return this
    }

    fun setPositiveButton(onClickListener: (view: View) -> (Unit)): BookDetailDialog {
        binding.btnYes.apply {
            this.text = context.getText(R.string.word_confirm)
            setOnClickListener(onClickListener)
            dismiss()
        }
        return this
    }

    fun setNegativeButton(onClickListener: (view: View) -> (Unit)): BookDetailDialog {
        binding.btnNo.apply {
            visibility = View.VISIBLE
            this.text = context.getText(R.string.word_cancel)
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