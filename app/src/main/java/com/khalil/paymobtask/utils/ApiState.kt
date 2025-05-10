package com.khalil.paymobtask.utils

sealed class ApiState<out T> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val message: String?, val code: Int? = null) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}