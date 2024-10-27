package ru.topbun.tasty.data.source.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.topbun.common.Const.BASE_URL
import ru.topbun.data.source.local.dataStore.Settings
import ru.topbun.data.source.local.dataStore.getToken
import ru.topbun.tasty.data.source.remote.auth.AuthApi
import ru.topbun.tasty.data.source.remote.category.CategoryApi
import ru.topbun.tasty.data.source.remote.recipe.RecipeApi

class ApiFactory(private val settings: Settings) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val contentType = "application/json".toMediaType()
    private var token: String = ""

    init {
        coroutineScope.launch {
            token = settings.getToken()
        }
    }

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
                .addHeader(HEADER_AUTH, "$HEADER_BEARER $token")
                .build()
            it.proceed(newRequest)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(client)
        .build()

    val recipeApi = retrofit.create<RecipeApi>()
    val categoryApi = retrofit.create<CategoryApi>()
    val authApi = retrofit.create<AuthApi>()

    companion object{
        private const val HEADER_AUTH = "Authentication"
        private const val HEADER_BEARER = "Bearer"
    }

}