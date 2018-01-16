package com.marcinstramowski.socialmeal.utils

import android.support.annotation.StringRes
import com.marcinstramowski.socialmeal.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * Util class to provide proper error string id message that depends on passed [throwable]
 *
 * Custom error messages
 */
class NetworkErrorMessageBuilder(private val throwable: Throwable) {

    private val httpExceptions = mutableListOf<HttpErrorMessageStringRes>()
    private val otherExceptions = mutableListOf<ErrorMessageStringRes>()

    /**
     * Adds custom http [errorCode] to be handled with passed [messageStringRes]
     */
    fun addHttpErrorMessage(errorCode: Int, @StringRes messageStringRes: Int): NetworkErrorMessageBuilder {
        httpExceptions.add(HttpErrorMessageStringRes(errorCode, messageStringRes))
        return this
    }

    /**
     * Adds custom [exception] to be handled with passed [messageStringRes]
     */
    fun <T : Exception> addErrorMessage(exception: T, @StringRes messageStringRes: Int): NetworkErrorMessageBuilder {
        otherExceptions.add(ErrorMessageStringRes(exception::class, messageStringRes))
        return this
    }

    /**
     * Returns suitable error message to passed [throwable]
     */
    fun getMessageStringId(): Int {
        return when (throwable) {
            is HttpException -> getHttpErrorMessageStringId(throwable)
            else -> getExceptionErrorMessageStringId()
        }
    }

    private fun getHttpErrorMessageStringId(httpException: HttpException): Int {
        val index = httpExceptions.indexOfFirst { it.httpExceptionCode == httpException.code() }
        if (index != -1) return httpExceptions[index].errorMessageStringRes
        return when (httpException.code()) {
            500 -> R.string.http_error_500
            else -> R.string.unknown_http_exception
        }
    }

    private fun getExceptionErrorMessageStringId(): Int {
        val exceptionIndex = otherExceptions.indexOfFirst { it.exception.jvmName == throwable.javaClass.name }
        if (exceptionIndex != -1) return otherExceptions[exceptionIndex].errorMessageStringRes
        return when (throwable) {
            is SocketTimeoutException -> R.string.timeout_exception
            is UnknownHostException -> R.string.unknown_host_exception
            else -> R.string.unknown_exception
        }
    }

    private data class HttpErrorMessageStringRes(val httpExceptionCode: Int, @StringRes val errorMessageStringRes: Int)
    private data class ErrorMessageStringRes(val exception: KClass<out Exception>, @StringRes val errorMessageStringRes: Int)
}