package com.example.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Message
import android.util.Log
import kotlinx.coroutines.*

class MyService: Service() {


    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        getMessage("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getMessage("onStartCommand")
        coroutine.launch {
            for (i in 0 until 25) {
                delay(1000)
                getMessage("Timer $i")
            }
        }
        return super.onStartCommand(intent, flags, startId)
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
    companion object {

        fun getIntent(context: Context):Intent {
            return Intent(context, MyService::class.java)
        }
    }

}