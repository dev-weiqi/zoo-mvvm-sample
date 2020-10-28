package dev.weiqi.zoo.util

import android.annotation.SuppressLint
import android.widget.Toast
import dev.weiqi.zoo.App

object ToastUtil {

    private var toast: Toast? = null

    fun short(any: Any?) {
        show(any?.toString(), Toast.LENGTH_SHORT)
    }

    fun long(any: Any?) {
        show(any?.toString(), Toast.LENGTH_LONG)
    }

    @SuppressLint("ShowToast")
    private fun show(message: CharSequence?, duration: Int) {
        if (message.isNullOrBlank()) return

        toast?.cancel()
        toast = Toast.makeText(App.context, message, duration).apply { show() }
    }
}