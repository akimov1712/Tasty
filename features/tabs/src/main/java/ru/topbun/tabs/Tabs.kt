package ru.topbun.tabs

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import ru.topbun.ui.R.*

sealed class Tabs(
    val title: String,
    val icon: Int
) {
    data object Recipes: Tabs(
        title = "Рецепты",
        icon = drawable.ic_tab_recipes
    )

    data object Category: Tabs(
        title = "Категории",
        icon = drawable.ic_tab_category
    )

    data object Favorite: Tabs(
        title = "Избранные",
        icon = drawable.ic_tab_favorite
    )

    data object Profile: Tabs(
        title = "Профиль",
        icon = drawable.ic_tab_profile
    )

}