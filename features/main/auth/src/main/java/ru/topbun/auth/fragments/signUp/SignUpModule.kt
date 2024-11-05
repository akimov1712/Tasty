package ru.topbun.auth.fragments.signUp

import org.koin.dsl.module
import ru.topbun.auth.fragments.login.LoginViewModel

val signUpModule = module {
    factory { SignUpViewModel(get()) }
}