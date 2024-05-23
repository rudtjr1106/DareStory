package com.darestory.presentation.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Vibrator
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.darestory.presentation.R
import com.darestory.presentation.ui.main.MainActivity

class AlarmService : Service() {

    companion object{
        var ringtone: Ringtone? = null
        var vibrator: Vibrator? = null

        fun stopAlarm() {
            ringtone?.stop()
            vibrator?.cancel()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "STOP_ALARM") {
            stopAlarm()
            stopSelf()
            return START_NOT_STICKY
        }
        val title = intent?.getStringExtra("title") ?: "Alarm"
        val body = intent?.getStringExtra("body") ?: "Alarm is ringing"

        // Foreground Notification 생성
        val channelId = "alarm_service_channel"
        createNotificationChannel(channelId)

        // 알림 터치 시 앱을 여는 인텐트
        val mainIntent = Intent(applicationContext, MainActivity::class.java).apply {
            putExtra("STOP_ALARM", true)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // 알림 끄기 버튼 클릭 시 호출되는 인텐트
        val stopIntent = Intent(this, AlarmService::class.java).apply {
            action = "STOP_ALARM"
        }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        // 커스텀 알림 뷰 설정
        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification).apply {
            setTextViewText(R.id.title, title)
            setTextViewText(R.id.text, body)
            setOnClickPendingIntent(R.id.stop_alarm_button, stopPendingIntent)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) // 알림 터치 시
            .build()

        checkPermission()
        notificationManager.notify(1, notificationBuilder)
        startAlarm()
        return START_STICKY
    }

    private fun startAlarm(){
        // 진동 시작
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(0, 1000, 1000)  // 대기, 진동, 대기
        vibrator?.vibrate(pattern, 1)

        // 알람음 시작
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone?.play()
    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
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
