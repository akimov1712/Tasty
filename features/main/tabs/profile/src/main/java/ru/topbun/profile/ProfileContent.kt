package ru.topbun.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.topbun.ui.R

data object ProfileScreen: Tab{

    @Composable
    override fun Content() {
        Text(text = "Profile")
    }

    override val options @Composable get() = TabOptions(
        index = 4u,
        title = "Profile",
        icon = painterResource(id = R.drawable.ic_tab_profile)
    )
}