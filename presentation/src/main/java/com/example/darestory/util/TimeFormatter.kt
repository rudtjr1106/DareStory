package com.example.darestory.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatter {
    fun getNowDateAndTime() : String{
        return SimpleDateFormat("yyyy.MM.dd/HH:mm:ss", Locale.getDefault()).format(Date()).toString()
    }
}