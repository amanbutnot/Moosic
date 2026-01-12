package io.github.amanbutnot.moosic

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import io.github.amanbutnot.moosic.presentation.SettingsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface {
            Navigator(SettingsScreen)
        }
    }
}
