package dev.weiqi.zoo.testtool

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner, Observer { it?.let(observer) })
}

inline fun <T> LiveData<T>.observeNullable(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner, Observer { observer(it) })
}