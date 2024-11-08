package ru.topbun.favorite.di

import org.koin.dsl.module
import ru.topbun.favorite.FavoriteViewModel

val favoriteModule = module {
    single { FavoriteViewModel(get()) }
}