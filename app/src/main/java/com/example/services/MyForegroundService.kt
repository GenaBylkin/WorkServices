package com.example.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Message
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyForegroundService: Service() {


    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        getMessage("onCreate")
        notificationChannel()
        startForeground(1, showNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getMessage("onStartCommand")
        coroutine.launch {
            for (i in 0 until 5) {
                delay(1000)
                getMessage("Timer $i")
            }
            stopSelf()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutine.cancel()
        getMessage("onDestroy")
    }

    private fun getMessage(message: String) {
        Log.d("Tag_Service",message)
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

    private fun showNotification() :Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }


    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_foreground"


        fun getIntent(context: Context):Intent {
            return Intent(context, MyForegroundService::class.java)
        }
    }

}