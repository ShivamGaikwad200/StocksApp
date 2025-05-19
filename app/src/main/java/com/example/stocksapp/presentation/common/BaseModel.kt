package com.example.stocksapp.presentation.common

sealed class BaseModel<out T> {
    data class Success<T>(val data:T):BaseModel<T>()
    data class Error(val error:String):BaseModel<Nothing>()
    data object Loading:BaseModel<Nothing>()
    companion object {
        fun <T> loading(): Result<T> = Loading
        fun <T> success(data: T): Result<T> = Success(data)
        fun <T> failure(message: String?): Result<T> = Failure(message)
    }
}
