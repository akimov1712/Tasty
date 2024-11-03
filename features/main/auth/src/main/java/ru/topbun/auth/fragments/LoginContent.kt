package ru.topbun.auth.fragments

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import ru.topbun.auth.R
import ru.topbun.ui.Colors
import ru.topbun.ui.R.*
import ru.topbun.ui.Typography

data object LoginScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Вход",
                style = Typography.Title1,
                color = Colors.BLACK,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            var email by remember { mutableStateOf("") }
            AppTextField(
                modifier = Modifier.height(48.dp),
                value = email,
                onValueChange = { email = it },
                placeholder = "Почта",
            )
            Spacer(modifier = Modifier.height(10.dp))
            var password by remember { mutableStateOf("") }
            var showPassword by remember { mutableStateOf(false) }
            AppTextField(
                modifier = Modifier.height(48.dp),
                value = password,
                onValueChange = { password = it },
                placeholder = "Пароль",
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                iconButton = {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (showPassword) drawable.ic_show
                                else drawable.ic_hide
                            ),
                            contentDescription = "Показать пароль",
                            tint = Colors.BLUE_GRAY
                        )
                    }
                }
            )
        }
    }
}

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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.5.dp, Colors.BLUE_GRAY, RoundedCornerShape(8.dp))
            .padding(padding),
        contentAlignment = Alignment.CenterStart
    ){
        if (value.isEmpty()){
            Text(
                text = placeholder,
                style = Typography.Placeholder1,
                color = Colors.BLUE_GRAY
            )
        }
        BasicTextField(
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
        iconButton?.let {
            Box(modifier = Modifier.align(Alignment.CenterEnd)){ it() }
        }
    }
}