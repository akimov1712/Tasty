package ru.topbun.recipe_by_category

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.navigation.main.MainScreenNavigator
import ru.topbun.navigation.main.MainScreenProvider
import ru.topbun.recipe_by_category.RecipeByCategoryState.RecipeScreenState.*
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography
import ru.topbun.ui.components.ErrorComponent
import ru.topbun.ui.components.RecipeItem

data class RecipeByCategoryScreen(val category: CategoryEntity): Screen{

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp)
        ) {
            val viewModel = getKoin().get<RecipeByCategoryViewModel>{ parametersOf(category.id) }
            val mainNavigator = koinScreenModel<MainScreenNavigator>()
            val state by viewModel.state.collectAsState()
            Header(category.name)
            Spacer(modifier = Modifier.height(24.dp))
            RecipeList(viewModel){
                val detailRecipeScreen =  ScreenRegistry.get(MainScreenProvider.DetailRecipe(it.id, false))
                mainNavigator.pushScreen(detailRecipeScreen)
            }

        }
    }

}

@Composable
private fun ColumnScope.RecipeList(viewModel: RecipeByCategoryViewModel, onClickRecipe: (RecipeEntity) -> Unit) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        when(val screenState = state.favoriteState){
            is RecipeByCategoryState.FavoriteScreenState.Error -> {
                LaunchedEffect(state.favoriteState) {
                    Toast.makeText(context, screenState.msg, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }

        if (state.recipes.isEmpty()) {
            if (state.recipeState == Loading) {
                CircularProgressIndicator(color = Colors.BLUE)
            }
            if (state.recipeState == Success) {
                Text(text = "Пусто", style = Typography.Title3)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.recipes){
                RecipeItem(it, {viewModel.changeFavorite(it.id, !it.isFavorite)}, onClickRecipe)
            }
            item(span = { GridItemSpan(2) }) {
                PaginationLoader(viewModel = viewModel, state = state)
            }
            val screenState = state.recipeState
            if (screenState is Error) {
                item(span = { GridItemSpan(2) }) {
                    ErrorComponent(text = screenState.msg) {
                        viewModel.loadRecipes()
                    }
                }
            }
        }
    }
}

@Composable
private fun PaginationLoader(viewModel: RecipeByCategoryViewModel, state: RecipeByCategoryState) {
    if (!state.isEndList) {
        val screenState = state.recipeState
        if (listOf(Success, Initial).contains(screenState)) {
            LaunchedEffect(state.recipes) {
                viewModel.loadRecipes()
            }
        } else if (screenState == Loading && state.recipes.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Colors.BLUE)
            }
        }
    }
}

@Composable
private fun Header(title: String) {
    val navigator = LocalNavigator.currentOrThrow
    Row {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(0.65f))
                .clickable(
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    navigator.pop()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = Colors.WHITE
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = title,
            style = Typography.Title1,
            color = Colors.BLACK
        )
    }

}