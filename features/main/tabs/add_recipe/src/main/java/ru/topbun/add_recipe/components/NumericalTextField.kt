package ru.topbun.add_recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppOutlinedTextField

@Composable
fun NumericalTextField(
    title: String,
    placeholderText: String,
    value: String,
    isLoading: Boolean = false,
    isImportant: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Number,
    errorText: String? = null,
    onValueChange: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = title,
                    color = Colors.GRAY_DARK,
                    style = Typography.General1
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
            AppOutlinedTextField(
                modifier = Modifier.weight(1f),
                value = value,
                errorText = errorText,
                onValueChange = onValueChange,
                enabled = !isLoading,
                placeholderText = placeholderText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Go
                ),
            )
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
fun NumericalText(
    title: String,
    value: String,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = title,
                color = Colors.BLACK,
                style = Typography.General1
            )
            Spacer(Modifier.width(20.dp))
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                text = value,
                style = Typography.Placeholder1,
                color = Colors.BLACK
            )
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.GRAY))
    }
}