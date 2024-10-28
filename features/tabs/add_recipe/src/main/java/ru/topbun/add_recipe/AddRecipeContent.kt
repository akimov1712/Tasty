package ru.topbun.add_recipe

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions


data object AddRecipeScreen: Tab{

    override val options @Composable get() = TabOptions(
        index = 2u,
        title = "",
        icon = null
    )

    @Composable
    override fun Content() {
        Text(text = "Add recipe")
    }
}