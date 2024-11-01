package ru.topbun.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Поиск"
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(Colors.GRAY, RoundedCornerShape(8.dp))
            .padding(start = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Поиск",
            tint = Colors.BLUE_GRAY
        )
        Box(modifier = Modifier.weight(1f)){
            if(value.isBlank()){
                Text(
                    text = placeholder,
                    style = Typography.Placeholder1,
                    color = Colors.BLUE_GRAY
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                textStyle = Typography.Placeholder1.copy(color = Colors.GRAY_DARK),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                cursorBrush = SolidColor(Colors.BLUE)
            )
        }
        if (value.isNotEmpty()){
            IconButton(onClick = { onValueChange("") }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Отмена",
                    tint = Colors.GRAY_DARK
                )
            }
        }
    }
}