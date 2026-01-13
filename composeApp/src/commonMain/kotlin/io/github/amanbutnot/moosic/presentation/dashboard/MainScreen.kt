package io.github.amanbutnot.moosic.presentation.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.graphics.shapes.RoundedPolygon
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.amanbutnot.moosic.presentation.SettingsScreen


object MainScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    override fun Content() {
        val nav = LocalNavigator.current
        val Cookie7Sided: RoundedPolygon = RoundedPolygon(7, radius = 3f)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                LargeTopAppBar(
                    title = {},
                    navigationIcon = {},
                    actions = {
                        IconButton(
                            onClick = { nav?.push(SettingsScreen) },
                            shapes = IconButtonShapes(Cookie7Sided.toShape()),
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