package ru.topbun.tasty.data.source.remote.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.topbun.tasty.data.source.remote.auth.dto.LoginDTO
import ru.topbun.tasty.data.source.remote.auth.dto.SignUpDTO
import ru.topbun.tasty.data.source.remote.auth.dto.TokenResponse

interface AuthApi {

    @POST("/login")
    suspend fun login(@Body login: LoginDTO): Response<TokenResponse>

    @POST("/signUp")
    suspend fun signUp(@Body signUp: SignUpDTO): Response<TokenResponse>

}