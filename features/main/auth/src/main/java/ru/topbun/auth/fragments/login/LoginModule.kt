package ru.topbun.auth.fragments.login

import org.koin.dsl.module

val loginModule = module {
    factory { LoginViewModel(get()) }
}