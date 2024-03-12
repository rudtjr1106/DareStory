package com.example.darestory

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.domain.model.vo.NotificationVo
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class FcmNotification : FirebaseMessagingService() {
    val JSON = MediaType.parse("application/json; charset=utf-8")
    val url = "https://fcm.googleapis.com/fcm/send"
    val serverKey =
        "AAAAOEg7Lbs:APA91bHh4OLhsjtPuYVok8lv2_pofFcC82ENeRyUiBW8c__Q0RI75wBA1O_stz95P1VTcZtwGwxh2BB7ZM-_DGsXXHciqFQ5mQrKKQxa34AYQHDkD7UfGEEykfcmeEQGawxBnWrjGbCa"
    private var okHttpClient = OkHttpClient()
    private var gson: Gson = Gson()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = data["title"]
        val body = data["body"]
        sendNotification(title, body)
    }

    private fun sendNotification(title: String?, body: String?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "channel_id"
        val channelName = "Channel Name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationId = 1
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    fun sendMessage(notification: NotificationVo) {

        val body = RequestBody.create(JSON, gson?.toJson(notification)!!)
        val request = Request
            .Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=$serverKey")
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
}