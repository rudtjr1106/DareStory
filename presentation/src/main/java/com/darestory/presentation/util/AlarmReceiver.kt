package com.darestory.presentation.util

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.os.Vibrator
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.darestory.presentation.R
import com.darestory.presentation.util.AlarmService
import com.darestory.presentation.util.DareLog

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceiver", "onReceive called")
        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")

        // WakeLock 사용하여 CPU 깨어있도록 설정
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag")
        wakeLock.acquire(10*60*1000L /*10 minutes*/)

        // 진동 시작
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(longArrayOf(0, 1000, 1000), -1)  // 반복 진동

        // 알람음 시작
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone: Ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone.play()

        DareLog.D("실행쓰쓰쓰")
        // Foreground Service 시작
        startForegroundService(context, title, body)

        // WakeLock 해제
        wakeLock.release()
    }

    private fun startForegroundService(context: Context, title: String?, body: String?) {
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("title", title)
            putExtra("body", body)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}
