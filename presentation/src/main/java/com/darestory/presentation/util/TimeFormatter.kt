package com.darestory.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

object TimeFormatter {
    fun getNowDateAndTime() : String{
        return SimpleDateFormat("yyyy.MM.dd/HH:mm:ss", Locale.getDefault()).format(Date()).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getYesterDay() : String {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        return yesterday.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAgoTime(time : String) : String{
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd/HH:mm:ss")
        val dateTime = LocalDateTime.parse(time, formatter)
        val now = LocalDateTime.now()
        val duration = Duration.between(dateTime, now)
        val minutes = duration.toMinutes()
        val agoTime = when {
            minutes < 1 -> "방금 전"
            minutes == 1L -> "1분 전"
            minutes < 60 -> "$minutes 분 전"
            else -> {
                val hours = duration.toHours()
                when {
                    hours == 1L -> "1시간 전"
                    hours < 24 -> "${hours}시간 전"
                    else -> {
                        val days = ChronoUnit.DAYS.between(dateTime.toLocalDate(), now.toLocalDate())
                        if (days == 1L) "어제" else "${days}일 전"
                    }
                }
            }
        }

        return agoTime
    }

    fun getDotsDate(date : String) : String {
        val year = date.substring(0, 4)
        val month = date.substring(4, 6)
        val day = date.substring(6)

        // 변환된 날짜 형식을 반환합니다.
        return "$year.$month.$day"
    }
}