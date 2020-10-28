package dev.weiqi.zoo.extension

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes

inline fun <reified T> Activity.argsOrDefault(
    key: String,
    crossinline defValue: () -> T
) = lazy {
    intent?.extras?.get(key) as? T ?: defValue()
}