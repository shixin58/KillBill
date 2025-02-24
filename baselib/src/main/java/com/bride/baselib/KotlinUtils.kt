package com.bride.baselib

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.view.Gravity
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

/**
 * @param size The scaled pixel size.
 */
fun showTopMessage(
    ctx: Context,
    msg: String,
    size: Float = 20f,
    drawableId: Int = R.drawable.toast_error,
    duration: Int = Toast.LENGTH_LONG
) {
    if (drawableId != -1) {
        val source = "{p1}$msg"
        val spannableString = SpannableString(source)
        val imageSpan = ImageSpan(ctx, drawableId)
        val sizeSpan = AbsoluteSizeSpan(ResUtils.sp2px(size))
        val iconStart = source.indexOf("{p1}")
        val iconEnd = source.indexOf("{p1}") + "{p1}".length
        spannableString.setSpan(imageSpan, iconStart, iconEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(sizeSpan, iconEnd, source.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(ctx, spannableString, duration)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
        toast.show()
    } else {
        val spannableString = SpannableString(msg)
        val sizeSpan = AbsoluteSizeSpan(ResUtils.sp2px(size))
        spannableString.setSpan(sizeSpan, 0, msg.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(ctx, spannableString, duration)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
        toast.show()
    }
}

fun showShortError(msg: String) {
    showTopMessage(appContext, msg, duration = Toast.LENGTH_SHORT)
}