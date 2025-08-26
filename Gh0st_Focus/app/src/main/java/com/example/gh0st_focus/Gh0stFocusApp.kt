package com.example.gh0st_focus

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class Gh0stFocusApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Notifications.CHANNEL_ID,
                "Gh0st Focus Alerts",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifies you when a timer completes"
                enableVibration(true)
                setShowBadge(false)
            }
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }
}
