package io.github.amanbutnot.moosic.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.graphics.shapes.RoundedPolygon
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.amanbutnot.moosic.presentation.SettingsScreen


object MainScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    override fun Content() {
        val nav = LocalNavigator.current
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                LargeTopAppBar(
                    title = {
                        Text("Dashboard", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.primary)
                    },
                    navigationIcon = {},
                    actions = {
                        FilledTonalIconButton(
                            onClick = { nav?.push(SettingsScreen) },
                            shapes = IconButtonDefaults.shapes(MaterialShapes.Ghostish.toShape()),
                            content = {
                                Icon(Icons.Default.Settings, Icons.Default.Settings.name)
                            }
                        )
                    }
                )
            },
            bottomBar = { FlexibleBottomAppBar() {} },
            floatingActionButton = {},
        ) { paddingValues ->

            AlbumTab(modifier = Modifier.padding(paddingValues))
        }
    }
}