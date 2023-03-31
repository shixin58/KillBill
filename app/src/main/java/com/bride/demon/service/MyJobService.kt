package com.bride.demon.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import com.bride.demon.Finals
import timber.log.Timber

/**
 * <p>Created by shixin on 2019-10-15.
 */
class MyJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Timber.tag(Finals.TAG_JOB_SCHEDULER).d("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Timber.tag(Finals.TAG_JOB_SCHEDULER).d("onStartJob %s", params?.jobId)
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Timber.tag(Finals.TAG_JOB_SCHEDULER).d("onStopJob %s", params?.jobId)
        return false
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Timber.tag(Finals.TAG_JOB_SCHEDULER).d("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Timber.tag(Finals.TAG_JOB_SCHEDULER).d("onDestroy")
        super.onDestroy()
    }
}