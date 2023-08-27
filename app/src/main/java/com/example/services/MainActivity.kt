package com.example.services

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var page = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonServices.setOnClickListener {
            startService(MyService.getIntent(this))
        }

        binding.foregroundService.setOnClickListener {
            ContextCompat.startForegroundService(this,
                MyForegroundService.getIntent(this)
            )
            // stop service inside-->
            // stopService(MyForegroundService.getIntent(this))
        }

        binding.intentService.setOnClickListener {
            ContextCompat.startForegroundService(this,
                MyIntentService.getIntent(this)
            )
        }


        binding.jobScheduler.setOnClickListener {
            val componentName = ComponentName(this, MyJobService::class.java)

            val jobInfo = JobInfo.Builder(MyJobService.JOB_ID,componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = MyJobService.getIntent(page++)
                jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
            }

            //jobScheduler.schedule(jobInfo)
        }


        binding.workManager.setOnClickListener {

            val request = OneTimeWorkRequest.from(MyWork::class.java)
            val requestBuilder = OneTimeWorkRequestBuilder<MyWork>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                ).build()
            //WorkManager.getInstance(this).
           WorkManager.getInstance(this).enqueueUniqueWork(
                    "MyWork.WORK_NAME"
                   ,ExistingWorkPolicy.APPEND
                   ,requestBuilder
           )
        }

    }


}