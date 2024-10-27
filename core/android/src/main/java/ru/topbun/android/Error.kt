package ru.topbun.android

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.Response
import ru.topbun.android.ClientException
import ru.topbun.android.ParseBackendResponseException
import ru.topbun.android.ServerException
import java.net.ConnectException
import java.net.SocketTimeoutException

suspend fun <T> exceptionWrapper(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: ClientException) {
        e.printStackTrace()
        throw e
    } catch (e: ServerException) {
        e.printStackTrace()
        throw e
    } catch (e: TimeoutCancellationException) {
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: ConnectTimeoutException) {
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: FailedExtractTokenException) {
        e.printStackTrace()
        throw FailedExtractTokenException()
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: IllegalStateException) {
        e.printStackTrace()
        throw ConnectException()
    } catch (e: Exception) {
        e.printStackTrace()
        throw ConnectException()
    }
}

fun <T> Response<T>.codeResultWrapper(): T {
    val error = Json.decodeFromString<ErrorDTO>(this.message())
    return when {
        this.code() in (400..499) -> throw ClientException(error.message)
        this.code() in (500..599) -> throw ServerException(error.message)
        else -> this.body() ?: throw ParseBackendResponseException()
    }
}

fun handlerTokenException(action: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    if (throwable is FailedExtractTokenException) action()
}

suspend fun wrapperStoreException(
    tryBlock: suspend () -> Unit,
    onFinally: () -> Unit = {},
    onError: (String) -> Unit
) {
    try {
        tryBlock()
    } catch (e: AccountInfoNotCompleteException) {
        onError("")
    } catch (e: ClientException) {
        onError(e.errorText)
    } catch (e: ServerException) {
        onError(e.errorText)
    } catch (e: RequestTimeoutException) {
        onError("")
    } catch (e: ConnectException) {
        onError("")
    } catch (e: ParseBackendResponseException) {
        onError("")
    } finally {
        onFinally()
    }
}

@Serializable
data class ErrorDTO(
    val code: Int,
    val message: String,
)
