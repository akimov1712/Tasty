package ru.topbun.add_recipe.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import ru.topbun.ui.components.ChoiceImage
import ru.topbun.ui.util.rippleClickable

@Composable
fun StepsContent(viewModel: AddRecipeViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Пошаговый рецепт",
            style = Typography.Title3,
            color = Colors.BLACK
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            state.steps.forEachIndexed { index, step ->
                StepItem(
                    stepNumber = index + 1,
                    text = step.text,
                    preview = step.preview,
                    onChangeText = { viewModel.changeStepText(it, index) },
                    onChangePreview = { viewModel.changeStepImage(it, index) },
                    onClickRemove = { viewModel.removeStep(index) },
                )
            }
        }
        if (state.steps.size < 20){
            AppButton(
                text = "Добавить шаг",
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(),
            ) {
                viewModel.addStep()
            }
        }
    }
}

@Composable
private fun StepItem(
    stepNumber: Int,
    text: String,
    preview: String?,
    onChangeText: (String) -> Unit,
    onChangePreview: (Uri?) -> Unit,
    onClickRemove: () -> Unit,
) {
      Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
          Text(
              text = stepNumber.toString(),
              color = Colors.BLACK,
              style = Typography.Title5
          )
          Column(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(5.dp)
          ) {
              AppOutlinedTextField(
                  modifier = Modifier
                      .fillMaxWidth()
                      .heightIn(min = 100.dp, max = 200.dp),
                  value = text,
                  onValueChange = { if ( it.length <= 1200) onChangeText(it) },
                  placeholderText = "Короткое описание",
                  singleLine = false
              )
              ButtonChoiceImage(
                  image = preview?.let { Uri.parse(it) },
                  onChangeImage = onChangePreview
              )
          }
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
                      .background(Colors.BLUE, CircleShape))
          }
      }
}

@Composable
private fun ButtonChoiceImage(
    image: Uri?,
    onChangeImage: (Uri?) -> Unit,
) {
    val contract = ActivityResultContracts.GetContent()
    val singleImagePicker = rememberLauncherForActivityResult(contract){ onChangeImage(it) }
    ChoiceImage(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        onClickChoiceImage = { singleImagePicker.launch("image/*") },
        onDeleteImage = { onChangeImage(null) },
        image = image
    )
}