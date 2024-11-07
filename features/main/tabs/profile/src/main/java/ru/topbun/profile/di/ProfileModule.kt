package ru.topbun.recipes.di

import org.koin.dsl.module
import ru.topbun.profile.ProfileViewModel

val profileModule = module {
    single { ProfileViewModel(get(), get()) }
}