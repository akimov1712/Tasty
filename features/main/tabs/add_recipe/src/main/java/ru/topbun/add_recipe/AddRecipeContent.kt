package ru.topbun.add_recipe

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import coil.compose.rememberAsyncImagePainter
import ru.topbun.add_recipe.components.IngredientsContent
import ru.topbun.add_recipe.components.NumericalDropMenuContent
import ru.topbun.add_recipe.components.NumericalText
import ru.topbun.add_recipe.components.NumericalTextField
import ru.topbun.add_recipe.components.StepsContent
import ru.topbun.common.isNumber
import ru.topbun.common.toStringOrBlank
import ru.topbun.domain.entity.recipe.DifficultyType
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AnimateTitle
import ru.topbun.ui.components.AppButton
import ru.topbun.ui.components.AppOutlinedTextField
import ru.topbun.ui.components.ChoiceImage
import ru.topbun.ui.util.rippleClickable


data object AddRecipeScreen: Screen{

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            val context = LocalContext.current
            val viewModel = koinScreenModel<AddRecipeViewModel>()
            val state by viewModel.state.collectAsState()
            AnimateTitle(text = "Добавить рецепт")
            Details(viewModel, false)
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(1.dp))
            NumericalContent(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            IngredientsContent(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            StepsContent(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                text = "Добавить рецепт",
                loading = state.screenState is AddRecipeState.AddRecipeScreenState.Loading
            ) {
                viewModel.addRecipe()
            }
            LaunchedEffect(state.screenState) {
                when(val screenState = state.screenState){
                    is AddRecipeState.AddRecipeScreenState.Error -> Toast.makeText(context, screenState.msg, Toast.LENGTH_SHORT).show()
                    AddRecipeState.AddRecipeScreenState.Success -> Toast.makeText(context, "Рецепт добавлен успешно", Toast.LENGTH_SHORT).show()
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun NumericalContent(viewModel: AddRecipeViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Числовые значения",
            style = Typography.Title3,
            color = Colors.BLACK
        )
        NumericalTextField(
            title = "Время приготовления",
            placeholderText = "0 мин",
            value = state.cookingTime.toStringOrBlank(),
            onValueChange = {
                if ((it.isEmpty() || it.isNumber()) && it.length < 5) {
                    viewModel.changeCookingTime(it.toIntOrNull())
                }
            }
        )
        Nutrients(state, viewModel)

        NumericalDropMenuContent(
            title = "Сложность",
            items = listOf<DifficultyType?>(null) + DifficultyType.values(),
            selectedItem = state.difficulty,
            onValueChange = viewModel::changeDifficulty
        )
    }
}

@Composable
private fun Nutrients(
    state: AddRecipeState,
    viewModel: AddRecipeViewModel
) {
    NumericalTextField(
        title = "Белков на 100 г",
        placeholderText = "0 г",
        value = state.protein.toStringOrBlank(),
        errorText = if (state.proteinIsError) "Поле не может быть пустым" else null,
        isImportant = true,
        keyboardType = KeyboardType.Decimal,
        onValueChange = { if (it.length < 4) viewModel.changeProtein(it) }
    )

    NumericalTextField(
        title = "Углеводов на 100 г",
        placeholderText = "0 г",
        value = state.carbs.toStringOrBlank(),
        errorText = if (state.carbsIsError) "Поле не может быть пустым" else null,
        isImportant = true,
        keyboardType = KeyboardType.Decimal,
        onValueChange = { if (it.length < 4) viewModel.changeCarbs(it) }
    )

    NumericalTextField(
        title = "Жиров на 100 г",
        placeholderText = "0 г",
        value = state.fat.toStringOrBlank(),
        errorText = if (state.fatIsError) "Поле не может быть пустым" else null,
        isImportant = true,
        keyboardType = KeyboardType.Decimal,
        onValueChange = { if (it.length < 4) viewModel.changeFat(it) }
    )

    NumericalText(
        title = "Ккал на 100 г",
        value = state.getKcal()?.toString() ?: "0",
    )
}

@Composable
private fun Details(viewModel: AddRecipeViewModel, isLoading: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val state by viewModel.state.collectAsState()
        Text(
            text = "Детали",
            style = Typography.Title3,
            color = Colors.BLACK
        )
        ButtonChoiceImage(viewModel)
        AppOutlinedTextField(
            value = state.title,
            onValueChange = { if ( it.length <= 48) viewModel.changeTitle(it) },
            enabled = !isLoading,
            placeholderText = "Название рецепта",
            errorText = if (state.titleIsError) "Название не может быть меньше 4 символов" else null
        )
        AppOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 200.dp),
            value = state.description,
            enabled = !isLoading,
            onValueChange = { if ( it.length <= 500) viewModel.changeDescr(it) },
            placeholderText = "Короткое описание",
            singleLine = false
        )
    }
}

@Composable
private fun ButtonChoiceImage(viewModel: AddRecipeViewModel) {
    val state by viewModel.state.collectAsState()
    val contract = ActivityResultContracts.GetContent()
    val singleImagePicker = rememberLauncherForActivityResult(contract){ viewModel.changeImage(it) }
    ChoiceImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        onClickChoiceImage = { singleImagePicker.launch("image/*") },
        onDeleteImage = { viewModel.changeImage(null) },
        image = state.imageUri
    )
}
