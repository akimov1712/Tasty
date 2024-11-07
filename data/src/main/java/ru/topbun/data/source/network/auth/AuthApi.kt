package ru.topbun.data.source.network.auth

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.topbun.data.source.network.ApiFactory
import ru.topbun.data.source.network.token
import ru.topbun.tasty.data.source.remote.auth.dto.LoginDTO
import ru.topbun.tasty.data.source.remote.auth.dto.SignUpDTO

class AuthApi(private val api: ApiFactory) {

    suspend fun login(login: LoginDTO) = api.client.post("/login"){
        setBody(login)
    }

    suspend fun signUp(signUp: SignUpDTO) = api.client.post("/signUp"){
        setBody(signUp)
    }

    suspend fun getAccountInfo(token: String) = api.client.get("/account"){
        token(token)
    }

}