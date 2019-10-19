package com.bride.demon.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.bride.demon.Finals

/**
 * <p>Created by shixin on 2019-10-15.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MyJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Log.d(Finals.TAG_JOB_SCHEDULER, "onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(Finals.TAG_JOB_SCHEDULER, "onStartJob ${params?.jobId}")
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(Finals.TAG_JOB_SCHEDULER, "onStopJob ${params?.jobId}")
        return false
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(Finals.TAG_JOB_SCHEDULER, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(Finals.TAG_JOB_SCHEDULER, "onDestroy")
        super.onDestroy()
    }
}