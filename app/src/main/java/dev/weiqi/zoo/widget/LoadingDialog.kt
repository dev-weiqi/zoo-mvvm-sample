@file:Suppress("DEPRECATION")

package dev.weiqi.zoo.widget

import android.app.ProgressDialog
import android.content.Context
import dev.weiqi.zoo.R

class LoadingDialog(
    context: Context,
    message: String = context.getString(R.string.plz_wait)
) : ProgressDialog(context, R.style.Theme_ProgressDialog) {

    init {
        setMessage(message)
        setCancelable(false)
    }
}
