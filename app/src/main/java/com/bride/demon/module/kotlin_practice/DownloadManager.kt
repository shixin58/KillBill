package com.bride.demon.module.kotlin_practice

import com.bride.baselib.*
import com.bride.demon.module.kotlin_practice.DownloadManager.DownloadStatus.Done
import com.bride.demon.module.kotlin_practice.DownloadManager.DownloadStatus.Error
import com.bride.demon.module.kotlin_practice.DownloadManager.DownloadStatus.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import okhttp3.Request
import java.io.File

object DownloadManager {
    private val downloadDirectory by lazy {
        File(appContext.filesDir, "download").also { it.mkdirs() }
    }

    sealed class DownloadStatus {
        object None : DownloadStatus()
        class Progress(val value: Int) : DownloadStatus()
        class Error(val throwable: Throwable) : DownloadStatus()
        class Done(val file: File) : DownloadStatus()
    }

    fun download(url: String, fileName: String): Flow<DownloadStatus> {
        val file = File(downloadDirectory, fileName)
        return flow {
            val request = Request.Builder().url(url).get().build()
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                response.body!!.let { body ->
                    val total = body.contentLength()// -1
                    var emittedProgress = 0L
                    file.outputStream().use { output ->
                        body.byteStream().use { input ->
                            input.copyTo(output) { bytesCopied ->
                                val progress = bytesCopied * 100 / total
                                LogUtils.d("download", "bytesCopied: $bytesCopied; total: $total; progress: $progress")
                                if (progress - emittedProgress > 5L) {
                                    emit(Progress(progress.toInt()))
                                    emittedProgress = progress
                                }
                            }
                        }
                    }
                    emit(Done(file))
                }
            } else {
                throw HttpException(response)
            }
        }.catch {
            file.delete()
            emit(Error(it))
        }.conflate()
    }
}