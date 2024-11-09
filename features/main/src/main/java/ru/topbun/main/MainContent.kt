package ru.topbun.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.topbun.navigation.main.MainScreenNavigator
import ru.topbun.tabs.TabsScreen
import ru.topbun.ui.Colors

data object MainScreen : Screen {


    @Composable
    override fun Content() {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(Colors.BLUE)
        systemUiController.setNavigationBarColor(Colors.WHITE, true)

        val viewModel = koinScreenModel<MainScreenNavigator>()
        val state by viewModel.state.collectAsState()

        Scaffold{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.WHITE)
                    .padding(it)
            ) {
                Navigator(
                    screen = TabsScreen,
                ) { navigator ->
                    CurrentScreen()
                    LaunchedEffect(key1 = state.updateCount) {
                        if (navigator.lastItemOrNull != state.screen) {
                            state.screen?.let {
                                navigator.push(it)
                            }
                        }
                    }
                    LaunchedEffect(state) {
                        if (state.screen == null && state.updateCount != 0){
                            navigator.pop()
                        }

                    }
                }
            }
        }
    }


}