package com.example.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.jar.Attributes.Name

class MyIntentService: IntentService(NAME) {


    override fun onCreate() {
        super.onCreate()
        getMessage("onCreate")
        notificationChannel()
        startForeground(1, showNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        getMessage("onDestroy")
    }

    private fun getMessage(message: String) {
        Log.d("Tag_Service",message)
    }

    override fun onHandleIntent(intent: Intent?) {
        getMessage("onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            getMessage("Timer $i")
        }
    }

    private fun notificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =  NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun showNotification() : Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }


    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_foreground"
        private const val NAME = "MyIntentService"

        fun getIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }

}