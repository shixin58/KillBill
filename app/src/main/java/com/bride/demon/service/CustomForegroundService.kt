package com.bride.demon.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.bride.baselib.LogUtils
import com.bride.demon.Finals

/**
 * <p>Created by shixin on 2019-10-28.
 */
class CustomForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("CustomForegroundService", "onCreate()")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundCompat()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.d("CustomForegroundService", "onStartCommand()")
        val type = intent?.getIntExtra(KEY_TYPE, 0)?:0
        if (type == 1) stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        LogUtils.d("CustomForegroundService", "onDestroy()")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundCompat() {
        try {
            val channel = NotificationChannel(Finals.CHANNEL_ID_MUSIC, Finals.CHANNEL_NAME_MUSIC, NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                    ?: return
            manager.createNotificationChannel(channel)

            val notification = Notification.Builder(this, Finals.CHANNEL_ID_MUSIC).build()
            startForeground(Finals.NOTIFICATION_ID_PLAY_MUSIC, notification)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val KEY_TYPE = "key_type"

        fun runForegroundSrv(context: Context) {
            val srvIntent = Intent(context, CustomForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(srvIntent)
            } else {
                context.startService(srvIntent)
            }
        }

        fun runForegroundTask(context: Context) {
            val srvIntent = Intent(context, CustomForegroundService::class.java)
            srvIntent.putExtra(KEY_TYPE, 1)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(srvIntent)
            } else {
                context.startService(srvIntent)
            }
        }
    }
}