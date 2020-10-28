package dev.weiqi.zoo.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) = observe(owner, { it?.let(observer) })

inline fun <T> LiveData<T>.observeNullable(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) = observe(owner, { observer(it) })