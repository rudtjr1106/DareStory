package com.darestory.presentation

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DareStoryApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        val config = Configuration.Builder()
//            .setMinimumLoggingLevel(android.util.Log.INFO)
//            .build()
//        WorkManager.initialize(this, config)
        Timber.plant(Timber.DebugTree())
    }
}