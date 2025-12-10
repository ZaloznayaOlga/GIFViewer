package com.olgaz.gifviewer.data.remote

sealed class BaseResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : BaseResult<T>()
    data class Error(val message: String) : BaseResult<Nothing>()

    fun <R : Any> transform(block: (T)->R): BaseResult<R> {
        return when(this) {
            is Error -> Error(message)
            is Success -> Success(block.invoke(data))
        }
    }
}

inline fun <T : Any> BaseResult<T>.onSuccessValue(block: (T) -> Unit): BaseResult<T> {
    if (this is BaseResult.Success) {
        block.invoke(data)
    }
    return this
}

inline fun <T: Any> BaseResult<T>.onErrorValue(block: (String) -> Unit): BaseResult<T> {
    if (this is BaseResult.Error) {
        block.invoke(message)
    }
    return this
}