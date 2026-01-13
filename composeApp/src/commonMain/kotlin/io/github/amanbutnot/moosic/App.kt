package io.github.amanbutnot.moosic

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.example.compose.MoosicTheme
import io.github.amanbutnot.moosic.presentation.SettingsScreen
import io.github.amanbutnot.moosic.presentation.dashboard.AlbumScreen
import io.github.amanbutnot.moosic.presentation.dashboard.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MoosicTheme {
        Surface {
            Navigator(MainScreen)
        }
    }
}
