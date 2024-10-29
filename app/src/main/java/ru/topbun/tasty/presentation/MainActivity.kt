package ru.topbun.tasty.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import ru.topbun.tabs.Tabs
import ru.topbun.tabs.TabsScreen
import ru.topbun.ui.Colors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold {
                Box(modifier = Modifier.fillMaxSize().background(Colors.WHITE).padding(it)){
                    Navigator(screen = TabsScreen)
                }
            }
        }
    }

}