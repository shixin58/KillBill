package com.bride.baselib

import android.app.Application
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import timber.log.Timber
import java.io.IOException
import java.lang.Thread.UncaughtExceptionHandler
import kotlin.coroutines.CoroutineContext

lateinit var appContext: Application

val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor {
    message -> Timber.d(message)
}.setLevel(BASIC)).build()

class HttpException(val response: Response) : IOException(response.toString())

class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Timber.e(exception, "Unhandled Coroutine Exception with ${context[Job]}")
    }
}

class GlobalThreadUncaughtExceptionHandler : UncaughtExceptionHandler {
    companion object {
        fun setup() {
            Thread.setDefaultUncaughtExceptionHandler(GlobalThreadUncaughtExceptionHandler())
        }
    }

    private val defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(t: Thread, e: Throwable) {
        Timber.d(e, "Uncaugth exception in thread: ${t.name}")
        defaultUncaughtExceptionHandler?.uncaughtException(t, e)
    }
}