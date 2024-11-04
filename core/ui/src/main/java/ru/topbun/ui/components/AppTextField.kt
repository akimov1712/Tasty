package ru.topbun.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    iconButton: (@Composable () -> Unit)? = null,
    padding: PaddingValues = PaddingValues(vertical = 14.dp, horizontal = 16.dp),
    enabled: Boolean = true,
    textStyle: TextStyle = Typography.Placeholder1.copy(color = Colors.GRAY_DARK),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Colors.BLUE_GRAY, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = modifier
                .weight(1f)
                .padding(padding)
        ){
            if (value.isEmpty()){
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
                enabled = enabled,
                textStyle = textStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = visualTransformation
            )

        }
        iconButton?.let {
            it()
        }
    }
}