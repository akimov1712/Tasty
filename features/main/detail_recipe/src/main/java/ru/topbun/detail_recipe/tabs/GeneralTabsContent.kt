package ru.topbun.detail_recipe.tabs

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.topbun.common.calculateKcal
import ru.topbun.domain.entity.recipe.IngredientsEntity
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.DashedDivider

@Composable
fun GeneralTabsScreen(recipe: RecipeEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(horizontal = 20.dp)
    ) {
        Nutrients(recipe)
        Description(recipe)
        Spacer(modifier = Modifier.height(20.dp))
        Ingredients(recipe)
    }
}

@Composable
private fun Ingredients(recipe: RecipeEntity) {
    Text(
        text = "Ингредиенты",
        style = Typography.Title2,
        color = Colors.BLACK
    )
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        recipe.ingredients.forEach {
            IngredientItem(it)
        }
    }
}

@Composable
private fun IngredientItem(ingr: IngredientsEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.weight(1.8f),
            text = ingr.name,
            style = Typography.General2,
            color = Colors.BLACK
        )
        DashedDivider(
            modifier = Modifier.weight(0.8f),
            dashHeight = 1.dp,
            dashWidth = 6.dp,
            gapWidth = 2.dp,
            color = Colors.GRAY_DARK
        )
        Text(
            modifier = Modifier.weight(1f),
            text = ingr.value,
            style = Typography.General2,
            color = Colors.BLACK,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun Description(recipe: RecipeEntity) {
    if (!recipe.description.isNullOrEmpty()){
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = recipe.description ?: "",
            style = Typography.General2,
            color = Colors.GRAY_DARK
        )
    }
}

@Composable
private fun Nutrients(recipe: RecipeEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val totalNutrients = recipe.getSumNutrients()
        NutrientItem(
            progress = getProgress(recipe.protein ?: 0, totalNutrients),
            value = recipe.protein?.toString() ?: "Не указан",
            title = "Белки",
            color = Color(0xffF8BA43)
        )

        NutrientItem(
            progress = getProgress(recipe.carbs ?: 0, totalNutrients),
            value = recipe.carbs?.toString() ?: "Не указан",
            title = "Углеводы",
            color = Color(0xff5187F1)
        )

        NutrientItem(
            progress = getProgress(recipe.fat ?: 0, totalNutrients),
            value = recipe.fat?.toString() ?: "Не указан",
            title = "Жиры",
            color = Color(0xffF15151)
        )
        KcalNutrients(recipe)
    }
}

@Composable
private fun KcalNutrients(recipe: RecipeEntity) {
    Column {
        Text(
            text = "Ккал",
            color = Colors.BLUE_GRAY,
            style = Typography.General1,
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = calculateKcal(
                recipe.protein?.toFloat(),
                recipe.carbs?.toFloat(),
                recipe.fat?.toFloat()
            )?.toString() ?: "Не указано",
            style = Typography.Option1,
            color = Colors.BLACK,
            fontSize = 16.sp
        )
    }
}

private fun getProgress(value: Int, total: Int) = value / total.toFloat()

@Composable
private fun NutrientItem(
    progress: Float,
    value: String,
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.width(75.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            strokeCap = StrokeCap.Round,
            color = color,
            trackColor = Color(0xffEDF1F7),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "$value g",
            style = Typography.Option1,
            color = Colors.BLACK,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = Typography.General1,
            color = Colors.BLUE_GRAY,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}