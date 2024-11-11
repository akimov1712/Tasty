package ru.topbun.add_recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import ru.topbun.add_recipe.R
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppOutlinedDropDownMenu
import ru.topbun.ui.util.localWidth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>NumericalDropMenuContent(
    title: String,
    items: List<T>,
    selectedItem: T,
    isImportant: Boolean = false,
    onValueChange: (T) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = title,
                    color = Colors.BLUE_GRAY,
                    style = Typography.Placeholder1
                )
                if (isImportant) {
                    Icon(
                        modifier = Modifier
                            .size(6.dp)
                            .align(Alignment.Top),
                        painter = painterResource(ru.topbun.ui.R.drawable.ic_important),
                        contentDescription = null,
                        tint = Colors.RED
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            var isDropMenuVisible by rememberSaveable { mutableStateOf(false) }
            var textFieldSize by remember { mutableStateOf(Size.Zero) }
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .weight(1f),
                expanded = isDropMenuVisible,
                onExpandedChange = { isDropMenuVisible = !isDropMenuVisible }
            ) {
                AppOutlinedDropDownMenu(
                    modifier = Modifier
                        .menuAnchor()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = selectedItem?.toString() ?: "",
                    placeholderText = "Не выбрано",
                    isOpen = isDropMenuVisible
                )
                DropMenuDifficulty(
                    modifier = Modifier.width(localWidth(textFieldSize.width)),
                    isDropMenuVisible = isDropMenuVisible,
                    items = items,
                    onValueChange = onValueChange,
                    onDismissRequest = { isDropMenuVisible = false }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.GRAY))
    }
}

@Composable
private fun <T> DropMenuDifficulty(
    modifier: Modifier = Modifier,
    isDropMenuVisible: Boolean,
    items: List<T>,
    onValueChange: (T) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier.background(Colors.WHITE),
        expanded = isDropMenuVisible,
        onDismissRequest = { onDismissRequest() },
    ) {
        items.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it?.toString()
                            ?: "Не выбрано",
                        color = Colors.GRAY_DARK,
                        style = Typography.Placeholder1
                    )
                },
                onClick = { onValueChange(it); onDismissRequest() }
            )
        }
    }
}