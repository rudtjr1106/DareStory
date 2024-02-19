package com.example.darestory.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.domain.model.sealed.DialogType

class CommonDialogFragment(private val dialogType: DialogType) : DialogFragment() {
    private lateinit var _binding : ViewBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = when(dialogType){
            DialogType.EditTextTwoButton -> TODO()
            DialogType.IconOneButton -> TODO()
            DialogType.TextOneButton -> TODO()
            DialogType.TextTwoButton -> TODO()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        return binding.root
    }

    // 인터페이스
    interface OnButtonClickListener {
        fun onButton1Clicked()
        fun onButton2Clicked()
        fun onButton3Clicked()
    }

    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener
}