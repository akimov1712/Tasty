package ru.topbun.add_recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.topbun.add_recipe.AddRecipeViewModel
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppButton
import ru.topbun.ui.components.AppOutlinedTextField
import ru.topbun.ui.util.rippleClickable

@Composable
fun IngredientsContent(viewModel: AddRecipeViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Ингредиенты",
            style = Typography.Title3,
            color = Colors.BLACK
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            state.ingredients.forEachIndexed { index, ingr ->
                IngredientItem(
                    name = ingr.name,
                    value = ingr.value,
                    onChangeName = { viewModel.changeIngredientName(it, index) },
                    onChangeValue = { viewModel.changeIngredientValue(it, index) },
                    onClickRemove = { viewModel.removeIngredient(index) },
                )
            }
        }
        if (state.ingredients.size < 24){
            AppButton(
                text = "Добавить игредиент",
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(),
            ) {
                viewModel.addIngredient()
            }
        }
    }
}

@Composable
private fun IngredientItem(
    name: String,
    value: String,
    onChangeName: (String) -> Unit,
    onChangeValue: (String) -> Unit,
    onClickRemove: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AppOutlinedTextField(
            modifier = Modifier.weight(2f),
            placeholderText = "Ингредиент",
            value = name,
            onValueChange = onChangeName
        )
        AppOutlinedTextField(
            modifier = Modifier.weight(1f),
            placeholderText = "Количество",
            value = value,
            onValueChange = onChangeValue
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Colors.WHITE)
                .rippleClickable { onClickRemove() },
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .size(12.dp, 2.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape))
        }

    }
}
