package ru.topbun.detail_recipe.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.entity.recipe.StepEntity
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppAsyncImage

@Composable
fun StepTabsScreen(recipe: RecipeEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(horizontal = 20.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            recipe.steps.forEachIndexed { index, step ->
                StepItem(
                    title = "Шаг ${step.order}/${recipe.steps.size}",
                    text = step.text,
                    image = step.preview
                )
            }
        }
    }
}

@Composable
fun StepItem(
    title: String,
    text: String,
    image: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = Typography.Title4,
            color = Colors.BLACK
        )
        Text(
            text = text,
            style = Typography.General2,
            color = Colors.GRAY_DARK
        )
        image?.let {
            if(it.isNotEmpty()){
                AppAsyncImage(
                    model = it,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = title
                )
            }
        }
    }
}