package ru.topbun.tabs

import android.provider.ContactsContract.Profile
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.topbun.add_recipe.AddRecipeScreen
import ru.topbun.category.CategoryScreen
import ru.topbun.favorite.FavoriteScreen
import ru.topbun.profile.ProfileScreen
import ru.topbun.recipes.RecipesScreen
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.components.BottomNavigationItem
import java.util.Locale.Category

object TabsScreen: Screen{

    @Composable
    override fun Content() {
            TabNavigator(RecipesScreen) {
                Scaffold(
                    content = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            CurrentTab()
                        }
                    },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .shadow(4.dp),
                            containerColor = Colors.WHITE,
                        ) {
                            TabNavigationItem(RecipesScreen)
                            TabNavigationItem(CategoryScreen)
                            TabAddRecipeItem(AddRecipeScreen)
                            TabNavigationItem(FavoriteScreen)
                            TabNavigationItem(ProfileScreen)

                        }
                    }
                )
            }
        }
}


@Composable
private fun TabAddRecipeItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    val selected = tabNavigator.current == tab

    val animateScaleButton by animateFloatAsState(targetValue = if (selected) 1.3f else 1f)
    val animateRotateIcon by animateFloatAsState(targetValue =  if (selected) 25f else 0f)
    val animateTransformY by animateDpAsState(targetValue =  if (selected) 90.dp else 0.dp)

    Box(
        modifier = Modifier.offset(x = 0.dp, y = -animateTransformY)
    ) {
        IconButton(
            modifier = Modifier
                .size(50.dp)
                .scale(animateScaleButton)
                .clip(CircleShape)
                .background(Colors.BLUE),
            onClick = {
                if (selected) Unit
                else tabNavigator.current = tab
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .rotate(animateRotateIcon),
                imageVector = Icons.Default.Add,
                contentDescription = "Добавить рецепт",
                tint = Colors.WHITE
            )
        }
    }

}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = tab.options.icon,
        title = tab.options.title
    )
}
