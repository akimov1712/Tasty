package ru.topbun.tasty.data.source.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.topbun.tasty.Const.BASE_URL

object ApiFactory {

    private const val HEADER_AUTH = "Authentication"
    private const val HEADER_BEARER = "Bearer"

    private val contentType = "application/json".toMediaType()
    private val client = OkHttpClient().newBuilder()
        .addInterceptor (
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        )
        .addInterceptor {
            val originalRequest = it.request()
            val newRequest = originalRequest
                .newBuilder()
                .addHeader(HEADER_AUTH, HEADER_BEARER)
                .build()
            it.proceed(newRequest)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(client)
        .build()

}