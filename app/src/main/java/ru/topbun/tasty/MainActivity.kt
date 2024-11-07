package ru.topbun.tasty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import org.koin.compose.KoinContext
import ru.topbun.main.MainScreen
import ru.topbun.ui.colorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme) {
                KoinContext {
                    MainScreen.Content()
                }
            }
        }
    }

}