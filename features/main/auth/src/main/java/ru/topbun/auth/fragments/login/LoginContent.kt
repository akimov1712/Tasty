package ru.topbun.auth.fragments.login

import android.app.ProgressDialog.show
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.topbun.auth.fragments.signUp.SignUpScreen
import ru.topbun.auth.fragments.login.LoginState.LoginScreenState.*
import ru.topbun.navigation.main.MainScreenNavigator
import ru.topbun.ui.Colors
import ru.topbun.ui.R.*
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppButton
import ru.topbun.ui.components.AppTextField
import ru.topbun.ui.util.noRippleClickable

data object LoginScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val context = LocalContext.current
            val viewModel = koinScreenModel<LoginViewModel>()
            val mainNavigator = koinScreenModel<MainScreenNavigator>()
            val state by viewModel.state.collectAsState()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Вход",
                style = Typography.Title1,
                color = Colors.BLACK,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Fields(viewModel)
            ButtonAccountNotExists()
            Spacer(modifier = Modifier.height(50.dp))
            ButtonLogin(viewModel)
            when(val screenState = state.loginScreenState){
                is Error -> LaunchedEffect(screenState) {
                    Toast.makeText(context, screenState.msg, Toast.LENGTH_SHORT).show()
                }
                Success -> {
                    mainNavigator.popScreen()
                }
                else -> {}
            }
        }
    }

}

@Composable
private fun ColumnScope.ButtonAccountNotExists(){
    val navigator = LocalNavigator.currentOrThrow
    Text(
        modifier = Modifier
            .padding(end = 5.dp, top = 5.dp)
            .align(Alignment.End)
            .noRippleClickable {
                navigator.push(SignUpScreen)
            },
        text = buildAnnotatedString {
            append("Нет аккаунта? ")
            pushStyle(SpanStyle(color = MaterialTheme.colorScheme.primary))
            append("Зарегистрироваться")
        },
        style = Typography.Tabs1,
        color = Colors.GRAY_DARK
    )
}

@Composable
private fun ButtonLogin(viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()
    val screenState = state.loginScreenState
    val enabled = screenState != Loading && state.validFields
    AppButton(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        text = "Войти",
        enabled = enabled,
        loading = state.loginScreenState == Loading
    ) {
        viewModel.login()
    }
}


@Composable
private fun Fields(viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = state.email,
        onValueChange = { if (it.length <= 40) viewModel.changeEmail(it) },
        placeholder = "Почта",
    )
    Spacer(modifier = Modifier.height(10.dp))
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = state.password,
        onValueChange = { if (it.length <= 48) viewModel.changePassword(it) },
        placeholder = "Пароль",
        visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        iconButton = {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { viewModel.changePasswordVisible() }
            ) {
                Icon(
                    painter = painterResource(
                        if (state.showPassword) drawable.ic_show
                        else drawable.ic_hide
                    ),
                    contentDescription = "Показать пароль",
                    tint = Colors.BLUE_GRAY
                )
            }
        }
    )
}
