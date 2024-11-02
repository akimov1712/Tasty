package ru.topbun.favorite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

data object FavoriteScreen: Tab {

    override val options @Composable get() = TabOptions(
        index = 3u,
        title = "Favorite",
        icon = painterResource(id = ru.topbun.ui.R.drawable.ic_tab_favorite)
    )

    @Composable
    override fun Content() {
        Text(text = "Favorite")
    }
}