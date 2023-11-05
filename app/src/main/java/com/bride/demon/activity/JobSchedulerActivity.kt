package com.bride.demon.activity

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bride.demon.Finals
import com.bride.demon.R
import com.bride.demon.service.MyJobService
import com.bride.ui_lib.BaseActivity
import timber.log.Timber

/**
 * 5.0开始支持，后台执行任务，非严格计时
 */
class JobSchedulerActivity : BaseActivity() {
    companion object {
        const val JOB_ID = 1

        fun openActivity(context: Context) {
            val intent = Intent(context, JobSchedulerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_scheduler)
    }

    fun onClickSchedule(view: View) {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val componentName = ComponentName(this, MyJobService::class.java)

        val builder = JobInfo.Builder(JOB_ID, componentName).apply {
            setMinimumLatency(60 * 1000L)
            setOverrideDeadline(120 * 1000L)
            setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            setRequiresDeviceIdle(true)
            setRequiresCharging(true)
        }

        jobScheduler.schedule(builder.build())

        Timber.tag(Finals.TAG_JOB_SCHEDULER).d("scheduled %s", JOB_ID)
        Toast.makeText(this, "Job $JOB_ID has been scheduled", Toast.LENGTH_SHORT).show()
    }

    fun onClickCancel(view: View) {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(JOB_ID)

        Timber.tag(Finals.TAG_JOB_SCHEDULER).d("canceled %s", JOB_ID)
        Toast.makeText(this, "Job $JOB_ID has been canceled", Toast.LENGTH_SHORT).show()
    }
}