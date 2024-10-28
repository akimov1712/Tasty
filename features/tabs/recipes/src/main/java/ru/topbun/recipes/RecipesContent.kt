package ru.topbun.recipes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

data object RecipesScreen: Tab {

    @Composable
    override fun Content() {
        Text(text = "Recipes")
    }
    
    override val options @Composable get() = TabOptions(
        index = 4u,
        title = "Recipes",
        icon = painterResource(id = ru.topbun.ui.R.drawable.ic_tab_recipes)
    )
}