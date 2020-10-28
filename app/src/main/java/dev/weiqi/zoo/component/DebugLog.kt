package dev.weiqi.zoo.component

import dev.weiqi.zoo.BuildConfig
import android.util.Log as NativeLog

private const val TAG = "[Joseph]"
private val isDebugMode = BuildConfig.DEBUG

fun logD(tag: String, message: String) {
    if (isDebugMode) NativeLog.d(tag, message)
}

fun logD(message: String) {
    if (isDebugMode) NativeLog.d(TAG, message)
}

fun logE(message: String, throwable: Throwable) {
    if (isDebugMode) NativeLog.e(TAG, message, throwable)
}

fun logE(throwable: Throwable) {
    if (isDebugMode) NativeLog.e(TAG, "[Error]", throwable)
}