package com.darestory.presentation.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.darestory.presentation.R
import com.darestory.presentation.databinding.CommonToastBinding
import com.darestory.domain.model.enums.ToastType

object DareToast {

    private const val MARGIN_BOTTOM = 72

    fun createToast(type : ToastType, context: Context, message: String, length : Int = Toast.LENGTH_LONG): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: CommonToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.common_toast, null, false)

        when(type){
            ToastType.COMPLETE -> binding.imageIcon.setImageResource(R.drawable.ic_check_check)
            ToastType.ERROR -> binding.imageIcon.setImageResource(R.drawable.ic_toast_error)
        }
        binding.textMessage.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, MARGIN_BOTTOM.px)
            duration = length
            view = binding.root
        }
    }

    fun createToast(type : ToastType, context: Context, message: Int, length : Int = Toast.LENGTH_LONG): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: CommonToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.common_toast, null, false)

        when(type){
            ToastType.COMPLETE -> binding.imageIcon.setImageResource(R.drawable.ic_check_check)
            ToastType.ERROR -> binding.imageIcon.setImageResource(R.drawable.ic_toast_error)
        }
        binding.textMessage.setText(message)

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, MARGIN_BOTTOM.px)
            duration = length
            view = binding.root
        }
    }
}