package com.example.services

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWork(
    context: Context,
    private val workerParameters: WorkerParameters
):Worker(context,workerParameters) {


    override fun doWork(): Result {
        getMessage("doWork")
        val page = workerParameters.inputData.getInt(PAGE,0)
            for (i in 0 until 5) {
                Thread.sleep(1000)
                getMessage("Timer $i $page")
            }
        return Result.success()
    }


    private fun getMessage(message: String) {
        Log.d("TAG_Service", message)
    }

    companion object {
        private const val PAGE = "page"

        }

}


