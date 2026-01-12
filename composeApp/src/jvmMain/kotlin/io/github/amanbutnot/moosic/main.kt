package io.github.amanbutnot.moosic

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Moosic",
    ) {
        App()
    }
}