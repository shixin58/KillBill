package com.bride.baselib

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.coroutines.resume

/**
 * 用不了android Log API。因此封装了println，便于查看时间和线程
 */
fun log(msg: Any) {
    println("${Date()} ${Thread.currentThread().name} $msg")
}

fun toast(msg: Any?) {
    Toast.makeText(appContext, msg.toString(), Toast.LENGTH_SHORT).show()
}

suspend fun Context.alert(title: String, msg: String): Boolean = suspendCancellableCoroutine { cancellableContinuation ->
    AlertDialog.Builder(this)
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
            cancellableContinuation.resume(false)
        }.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            cancellableContinuation.resume(true)
        }.setTitle(title)
        .setMessage(msg)
        .setOnCancelListener {
            cancellableContinuation.resume(false)
        }.create()
        .also { dialog ->
            cancellableContinuation.invokeOnCancellation { dialog.dismiss() }
        }.show()
}

inline fun InputStream.copyTo(out: OutputStream, bufferSize: Int = DEFAULT_BUFFER_SIZE, progress: (Long)-> Unit): Long {
    var bytesCopied: Long = 0
    val buffer = ByteArray(bufferSize)
    var bytes = read(buffer)
    while (bytes >= 0) {
        out.write(buffer, 0, bytes)
        bytesCopied += bytes
        bytes = read(buffer)

        progress(bytesCopied)
    }
    return bytesCopied
}