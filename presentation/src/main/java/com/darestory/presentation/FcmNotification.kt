package com.darestory.presentation

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.darestory.domain.model.vo.NotificationVo
import com.darestory.presentation.util.AlarmReceiver
import com.darestory.presentation.util.DareLog
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.Calendar

class FcmNotification : FirebaseMessagingService() {
    val JSON = MediaType.parse("application/json; charset=utf-8")
    val url = "https://fcm.googleapis.com/fcm/send"
    private var okHttpClient = OkHttpClient()
    private var gson: Gson = Gson()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = data["title"] ?: ""
        val body = data["body"] ?: ""

        DareLog.D("여기 FCM인디유")
        setAlarm(this, title, body)
    }

    fun sendMessage(notification: NotificationVo) {

        val body = RequestBody.create(JSON, gson?.toJson(notification)!!)
        val request = Request
            .Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=${BuildConfig.fcm_server_key}")
            .url(url)
            .post(body)
            .build()
        okHttpClient?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                println(response?.body()?.string())
            }
        })
    }

    private fun setAlarm(context: Context, title: String, body: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("body", body)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.SECOND, 5)  // 5초 후 알람 발생
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}