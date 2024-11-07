package ru.topbun.data.source.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ru.topbun.common.Const.BASE_URL
import ru.topbun.data.source.local.dataStore.Settings
import ru.topbun.data.source.local.dataStore.getToken
import ru.topbun.data.source.network.ApiFactory.Companion.HEADER_AUTH
import ru.topbun.data.source.network.ApiFactory.Companion.HEADER_BEARER

class ApiFactory {

    val client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }

        defaultRequest {
            contentType(ContentType.Application.Json.withParameter("charset", "utf-8"))
            url(BASE_URL)
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.BODY
        }
    }

    companion object{
        const val HEADER_AUTH = "Authorization"
        const val HEADER_BEARER = "Bearer"
    }

}

fun HttpRequestBuilder.token(token: String) = header(HEADER_AUTH, "$HEADER_BEARER $token")
