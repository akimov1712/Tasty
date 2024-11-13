package ru.topbun.detail_recipe

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import ru.topbun.common.convertCookingTime
import ru.topbun.common.improveImageQuality
import ru.topbun.detail_recipe.DetailRecipeState.DetailRecipeScreenState.Error
import ru.topbun.detail_recipe.DetailRecipeState.DetailRecipeScreenState.Loading
import ru.topbun.detail_recipe.DetailRecipeState.DetailRecipeScreenState.Success
import ru.topbun.detail_recipe.tabs.DetailRecipeTabs
import ru.topbun.detail_recipe.tabs.GeneralTabsScreen
import ru.topbun.detail_recipe.tabs.StepTabsScreen
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.navigation.main.MainScreenNavigator
import ru.topbun.navigation.main.MainScreenProvider
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppAsyncImage
import ru.topbun.ui.components.AppButton
import ru.topbun.ui.components.AppIconButton
import ru.topbun.ui.components.DialogWrapper
import ru.topbun.ui.components.ErrorComponent
import ru.topbun.ui.util.noRippleClickable
import ru.topbun.ui.util.rippleClickable

data class DetailRecipeScreen(val recipeId: Int, val fromCache: Boolean) : Screen {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            val context = LocalContext.current
            val koin = getKoin()
            val viewModel = rememberScreenModel { koin.get<DetailRecipeViewModel> { parametersOf(recipeId, fromCache) } }
            val state by viewModel.state.collectAsState()
            val screenState = state.screenState
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Header(state,viewModel)
                if(screenState is Success) {
                    Body(state, screenState.recipe, viewModel){

                    }
                }

            }
            when (screenState) {
                is Error -> ErrorComponent(text = screenState.msg) {
                    viewModel.loadRecipe()
                }
                Loading -> CircularProgressIndicator(color = Colors.BLUE)
                else -> {}
            }
            when (state.deleteState) {

                DetailRecipeState.DeleteRecipeState.Success -> {
                    LaunchedEffect(state.deleteState) {
                        Toast.makeText(context, "Рецепт удален", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
        }
    }

    @Composable
    private fun Body(
        state: DetailRecipeState,
        recipe: RecipeEntity,
        viewModel: DetailRecipeViewModel,
        onClickCategory: (CategoryEntity) -> Unit,
    ) {
        if (state.showModalCategories){
            CategoriesModal(
                categories = recipe.categories,
                onDismiss = { viewModel.hideCategoriesModal()},
                onClickCategory = onClickCategory
            )
        }
        if (state.showModalDeleteRecipe){
            DialogConfirmDeleteRecipe(
                onClickDelete = { viewModel.deleteRecipe(recipe.id) },
                onDismissDialog = { viewModel.hideDeleteRecipeModal() }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = -32.dp)
        ) {
            Table(viewModel, recipe)
            Spacer(modifier = Modifier.height(20.dp))
            TabRow(
                tabs = state.tabs.map { it.title },
                selectedIndex = state.selectedTabIndex
            ) { viewModel.changeTabIndex(it) }
            when (state.tabs.elementAt(state.selectedTabIndex)) {
                DetailRecipeTabs.GENERAL -> GeneralTabsScreen(recipe = recipe)
                DetailRecipeTabs.STEP_BY_STEP -> StepTabsScreen(recipe = recipe)
            }
        }
    }
}

@Composable
private fun TabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelectedIndex: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, item ->
            val color = if (selectedIndex == index) Colors.BLUE else Colors.BLUE_GRAY
            Text(
                modifier = Modifier
                    .noRippleClickable {
                        onSelectedIndex(index)
                    }
                    .padding(horizontal = 20.dp),
                text = item,
                style = Typography.Tabs2,
                color = color
            )
        }
    }
}

@Composable
fun Table(viewModel: DetailRecipeViewModel, recipe: RecipeEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Colors.WHITE)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            text = recipe.title,
            style = Typography.Title4,
            color = Colors.BLACK,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.GRAY)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val textTags = if (recipe.categories.isEmpty()) "Отсутствуют"
            else "${recipe.categories.first().name.take(10)} + ${recipe.categories.size - 1}"
            CharactItem(
                icon = painterResource(id = R.drawable.ic_tag),
                text = textTags
            ) {
                viewModel.showCategoriesModal()
            }
            CharactItem(
                icon = painterResource(id = R.drawable.ic_time),
                text = recipe.timeCooking.convertCookingTime()
            )
            CharactItem(
                icon = painterResource(id = R.drawable.ic_difficulty),
                text = recipe.difficulty?.toString() ?: "Не указано"
            )
        }
    }
}

@Composable
private fun CharactItem(
    icon: Painter,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Colors.GRAY_DARK
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = Typography.Descr1,
            color = Colors.BLACK
        )
    }

}

@Composable
private fun Header(state: DetailRecipeState, viewModel: DetailRecipeViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.4f)
    ) {
        val screenState = state.screenState
        if (screenState is Success) {
            AppAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = screenState.recipe.image?.improveImageQuality(),
                contentDescription = screenState.recipe.title,
                contentScale = ContentScale.Crop
            )
        }
        AppIconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            icon = painterResource(id = R.drawable.ic_back)
        ) {
            navigator.pop()
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AppIconButton(
                icon = if (screenState !is Success) Icons.Outlined.FavoriteBorder else {
                    if (screenState.recipe.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                }
            ) {
                if (screenState is Success) {
                    viewModel.changeFavorite(!screenState.recipe.isFavorite)
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (state.showButtonDeleteRecipe){
                    AppIconButton(
                        icon = Icons.Filled.Delete
                    ) {
                        viewModel.showDeleteRecipeModal()
                    }
                }
                AppIconButton(
                    icon = painterResource(if (state.buttonSaveIsSave) R.drawable.ic_download else R.drawable.ic_close)
                ) {
                    if((screenState is Success)){
                        if(state.buttonSaveIsSave){
                            viewModel.saveRecipe(screenState.recipe)
                        } else {
                            viewModel.deleteRecipe(screenState.recipe.id, true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoriesModal(
    categories: List<CategoryEntity>,
    onDismiss: () -> Unit,
    onClickCategory: (CategoryEntity) -> Unit,
) {
    DialogWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .background(color = Colors.WHITE, RoundedCornerShape(12.dp))
            .padding(16.dp),
        onDismissDialog = onDismiss
    ) {
        LazyColumn(
            modifier = Modifier.align(Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(items = categories) {
                CategoriesDialogItem(it, onClickCategory)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onDismiss,
        ) {
            Text(
                text = "Закрыть",
                style = Typography.Title2,
                color = Colors.BLUE
            )
        }
    }
}

@Composable
private fun CategoriesDialogItem(category: CategoryEntity, onClick: (CategoryEntity) -> Unit) {
    Row(
        modifier = Modifier.noRippleClickable { onClick(category) },
        verticalAlignment = Alignment.CenterVertically
    ){
        AppAsyncImage(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(8.dp)),
            placeholderSize = DpSize(32.dp ,25.dp),
            model = category.image,
            contentDescription = category.name
        )
        Spacer(modifier = Modifier.widthIn(10.dp))
        Text(
            text = category.name,
            style = Typography.Title2,
            color = Colors.BLACK
        )
    }
}

@Composable
fun DialogConfirmDeleteRecipe(
    onClickDelete: () -> Unit,
    onDismissDialog: () -> Unit
) {
    DialogWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(color = Colors.WHITE, RoundedCornerShape(12.dp))
            .padding(20.dp),
        onDismissDialog = onDismissDialog
    ) {
        Text(
            text = "Вы уверены, что хотите удалить рецепт?",
            color = Colors.BLACK,
            style = Typography.Title1.copy(fontSize = 22.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.align(Alignment.End)
        ){
            TextButton(
                onClick = onDismissDialog,
            ) {
                Text(
                    text = "Отмена",
                    style = Typography.Title2,
                    color = Colors.BLUE
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            TextButton(
                onClick = onClickDelete,
                colors = ButtonDefaults.textButtonColors(contentColor = Colors.RED)
            ) {
                Text(
                    text = "Удалить",
                    style = Typography.Title2,
                    color = Colors.RED
                )
            }
        }
    }
}