package ru.topbun.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography

@Composable
fun AppOutlinedDropDownMenu(
    modifier: Modifier = Modifier,
    value: String,
    placeholderText: String,
    textStyle: TextStyle = Typography.Placeholder1,
    isOpen: Boolean = false
) {
    AppOutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {},
        readOnly = true,
        placeholderText = placeholderText,
        textStyle = textStyle,
        singleLine = true,
        trailingIcon = {
            val animateRotate by animateFloatAsState(if (isOpen) 180f else 0f)
            Icon(
                modifier = Modifier.rotate(animateRotate),
                painter = painterResource(R.drawable.ic_drop_down),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}