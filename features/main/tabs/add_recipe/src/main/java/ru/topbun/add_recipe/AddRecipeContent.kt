package ru.topbun.add_recipe

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AnimateTitle
import ru.topbun.ui.components.AppOutlinedTextField
import ru.topbun.ui.util.rippleClickable
import java.net.URI


data object AddRecipeScreen: Screen{

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            val viewModel = koinScreenModel<AddRecipeViewModel>()
            AnimateTitle(text = "Добавить рецепт")
            Details(viewModel, false)
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(1.dp))
//            NumericalContent(component, isLoading){ isOpenModalChoiceTags = true }
        }

    }
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
            onValueChange = viewModel::changeTitle,
            enabled = !isLoading,
            placeholderText = "Название рецепта",
            errorText = if (state.titleIsError) "Название не может быть меньше 8 символов" else null
        )
        AppOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 200.dp),
            value = state.description,
            enabled = !isLoading,
            onValueChange = viewModel::changeDescr,
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.WHITE)
            .border(2.dp, Colors.GRAY, RoundedCornerShape(16.dp))
            .rippleClickable {
                singleImagePicker.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        state.imageUri?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Colors.BLUE_GRAY.copy(0.2f))
                    .rippleClickable { viewModel.changeImage(null) }
                    .padding(8.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Colors.WHITE
            )
        } ?: Icon(
            modifier = Modifier.size(72.dp),
            painter = painterResource(ru.topbun.ui.R.drawable.ic_send_image),
            contentDescription = null,
            tint = Colors.BLUE
        )
    }
}