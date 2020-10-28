package dev.weiqi.zoo.model

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

typealias StatefulLiveData<T> = LiveData<State<T>>
typealias MutableStatefulLiveData<T> = MutableLiveData<State<T>>

sealed class State<out T> {

    data class Loading(val show: Boolean) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
    data class Error(val throwable: Throwable) : State<Nothing>()
    object Empty : State<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(throwable: Throwable) = Error(throwable)
        fun empty() = Empty
        fun loading(show: Boolean) = Loading(show)
        fun <T> successOrEmpty(list: List<T>) = if (list.isEmpty()) Empty else Success(list)

        @VisibleForTesting
        fun <T> State<T>.isSuccessfully(expected: T) = this is Success && this.data == expected

        @VisibleForTesting
        fun State<*>.isError() = this is Error

        @VisibleForTesting
        fun State<*>.isEmpty() = this is Empty
    }

}