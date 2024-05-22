package com.darestory.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.darestory.presentation.R

class AlarmService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val title = intent?.getStringExtra("title") ?: "Alarm"
        val body = intent?.getStringExtra("body") ?: "Alarm is ringing"

        // Foreground Notification 생성
        val channelId = "alarm_service_channel"
        createNotificationChannel(channelId)
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        startForeground(1, notification)

        // 진동 시작
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val handler = Handler(Looper.getMainLooper())

        // 진동 패턴 정의
        val vibrateRunnable = object : Runnable {
            var repeatCount = 0

            override fun run() {
                if (repeatCount < 3) { // 3번 반복
                    vibrator.vibrate(1000) // 1초간 진동
                    repeatCount++
                    handler.postDelayed(this, 2000) // 2초 후 다시 실행
                }
            }
        }
        handler.post(vibrateRunnable)

        // 알람음 시작
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone: Ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone.play()

        return START_STICKY
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Alarm Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
