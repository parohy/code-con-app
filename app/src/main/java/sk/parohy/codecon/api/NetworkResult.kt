package sk.parohy.codecon.api

import android.view.View

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T): NetworkResult<T>()
    data class Failure(val ex: Exception): NetworkResult<Nothing>()
    object Loading: NetworkResult<Nothing>()
}

val NetworkResult<*>.isSuccessful: Boolean get() = this is NetworkResult.Success

fun <T> success(value: T): NetworkResult.Success<T> = NetworkResult.Success(value)
fun  failure(ex: Exception): NetworkResult.Failure = NetworkResult.Failure(ex)

fun success(): NetworkResult.Success<Unit> = NetworkResult.Success(Unit)
