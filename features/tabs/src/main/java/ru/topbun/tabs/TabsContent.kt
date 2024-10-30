package ru.topbun.tabs

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScaleTransition
import cafe.adriel.voyager.transitions.SlideTransition
import ru.topbun.add_recipe.AddRecipeScreen
import ru.topbun.android.pushFront
import ru.topbun.category.CategoryScreen
import ru.topbun.favorite.FavoriteScreen
import ru.topbun.profile.ProfileScreen
import ru.topbun.recipes.RecipesScreen
import ru.topbun.ui.Colors
import ru.topbun.ui.components.BottomNavigationItem

object TabsScreen: Screen{

    @Composable
    override fun Content() {
        Navigator(RecipesScreen){ navigator ->
            Scaffold(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Colors.WHITE)
                            .padding(it)
                    ) {
                        navigator.lastItem.Content()
                    }
                },
                bottomBar = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        BottomAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .shadow(4.dp),
                            containerColor = Colors.WHITE,
                        ) {
                            TabNavigationItem(
                                screen = RecipesScreen,
                                tab = Tabs.Recipes
                            )
                            TabNavigationItem(
                                screen = CategoryScreen,
                                tab = Tabs.Category
                            )
                            Spacer(Modifier.weight(1f, true))
                            TabNavigationItem(
                                screen = FavoriteScreen,
                                tab = Tabs.Favorite
                            )
                            TabNavigationItem(
                                screen = ProfileScreen,
                                tab = Tabs.Profile
                            )
                        }
                        TabAddRecipeItem(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            )
        }
    }

    @Composable
    private fun TabAddRecipeItem(
        modifier: Modifier = Modifier
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val selected = navigator.lastItem == AddRecipeScreen

        val animateScaleButton by animateFloatAsState(targetValue = if (!selected) 1.3f else 1f)
        val animateRotateIcon by animateFloatAsState(targetValue = if (selected) -45f else 0f)
        val animateTransformY by animateDpAsState(targetValue = if (!selected) (-20).dp else 0.dp)

        Box(
            modifier = modifier
                .offset(y = animateTransformY)
        ) {
            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .scale(animateScaleButton)
                    .clip(CircleShape)
                    .background(Colors.BLUE),
                onClick = {
                    if (selected) {
                        navigator.pop()
                    } else {
                        navigator.pushFront(AddRecipeScreen)
                    }
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
}


@Composable
private fun RowScope.TabNavigationItem(tab: Tabs, screen: Screen) {
    val navigator = LocalNavigator.currentOrThrow
    val selected = navigator.lastItem == screen

    BottomNavigationItem(
        selected = selected,
        onClick = { navigator.pushFront(screen) },
        icon = painterResource(id = tab.icon),
        title = tab.title
    )
}
