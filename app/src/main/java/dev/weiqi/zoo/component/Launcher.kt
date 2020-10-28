package dev.weiqi.zoo.component

import android.content.Context
import android.content.Intent
import android.net.Uri


object Launcher {

    fun openBrowser(context: Context, url: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        context.startActivity(intent)
    }
}