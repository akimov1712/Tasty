package ru.topbun.data

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.Serializable
import org.apache.http.conn.ConnectTimeoutException
import ru.topbun.android.ClientException
import ru.topbun.android.ConnectException
import ru.topbun.android.FailedExtractTokenException
import ru.topbun.android.ParseBackendResponseException
import ru.topbun.android.RequestTimeoutException
import ru.topbun.android.ServerException
import java.net.SocketTimeoutException

@Serializable
data class ErrorDTO(
    val code: Int,
    val message: String,
)

suspend fun <T>exceptionWrapper(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: ClientException){
        e.printStackTrace()
        throw e
    } catch (e: ServerException){
        e.printStackTrace()
        throw e
    } catch (e: TimeoutCancellationException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: JsonConvertException){
        e.printStackTrace()
        throw ParseBackendResponseException()
    } catch (e: ConnectTimeoutException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: HttpRequestTimeoutException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: NoTransformationFoundException){
        e.printStackTrace()
        throw ParseBackendResponseException()
    } catch (e: DoubleReceiveException){
        e.printStackTrace()
        throw ParseBackendResponseException()
    } catch (e: FailedExtractTokenException){
        e.printStackTrace()
        throw FailedExtractTokenException()
    } catch (e: SocketTimeoutException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: IllegalStateException){
        e.printStackTrace()
        throw ConnectException()
    } catch (e: Exception){
        e.printStackTrace()
        throw ConnectException()
    }
}

suspend fun HttpResponse.codeResultWrapper() = when(this.status.value) {
    401 -> throw FailedExtractTokenException()
    in (400..499) -> throw ClientException(createErrorMessage(this))
    in (500..599) -> throw ServerException(createErrorMessage(this))
    else -> this
}


private suspend fun createErrorMessage(httpResponse: HttpResponse) = httpResponse.body<ErrorDTO>().message


