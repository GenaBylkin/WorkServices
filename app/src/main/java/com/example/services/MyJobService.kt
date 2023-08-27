package com.example.services


import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*

class MyJobService: JobService() {


    private val coroutine = CoroutineScope(Dispatchers.Main)


    override fun onCreate() {
        super.onCreate()
        getMessage("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        getMessage("onStartJob")
        coroutine.launch {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var work =  params?.dequeueWork()
            while (work != null) {
                val page = work.intent.getIntExtra(PAGE,0)
                for (i in 0 until 25) {
                    delay(1000)
                    getMessage("Timer $i $page")
                }
                params?.completeWork(work)
                work = params?.dequeueWork()
            }
            jobFinished(params,false)
        }
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        getMessage("onStopJob")
        return true
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
        const val JOB_ID = 111
        private const val PAGE = "page"

        fun getIntent(page: Int):Intent {
            return Intent().apply {
                putExtra(PAGE,page)
            }
        }

    }
}