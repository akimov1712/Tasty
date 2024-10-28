package ru.topbun.category

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.topbun.ui.R

data object CategoryScreen: Tab {

    override val options @Composable get() = TabOptions(
        index = 1u,
        title = "Category",
        icon = painterResource(id = R.drawable.ic_tab_category)
    )

    @Composable
    override fun Content() {
        Text(text = "Category")
    }
}