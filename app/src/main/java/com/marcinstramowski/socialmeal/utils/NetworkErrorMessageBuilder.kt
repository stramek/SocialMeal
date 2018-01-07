package com.marcinstramowski.socialmeal.utils

import android.support.annotation.StringRes
import com.marcinstramowski.socialmeal.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * Created by marcinstramowski on 07.01.2018.
 */
class NetworkErrorMessageBuilder(private val throwable: Throwable) {

    private val httpExceptions = mutableListOf<HttpErrorMessageStringRes>()
    private val otherExceptions = mutableListOf<ErrorMessageStringRes>()

    fun addHttpErrorMessage(errorCode: Int, @StringRes messageStringres: Int): NetworkErrorMessageBuilder {
        httpExceptions.add(HttpErrorMessageStringRes(errorCode, messageStringres))
        return this
    }

    fun <T : Exception> addErrorMessage(exception: T, @StringRes messageStringres: Int): NetworkErrorMessageBuilder {
        otherExceptions.add(ErrorMessageStringRes(exception::class, messageStringres))
        return this
    }

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