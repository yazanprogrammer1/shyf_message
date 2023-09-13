package com.example.shyf_message.screenstate

sealed class ScreenState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): ScreenState<T>(data)
    class Loading<T>(data: T? = null): ScreenState<T>(data)
    class Error<T>(data: T? = null, message: String): ScreenState<T>(data,message)
}