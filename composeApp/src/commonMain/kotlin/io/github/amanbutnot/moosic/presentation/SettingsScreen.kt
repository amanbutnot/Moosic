package io.github.amanbutnot.moosic.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.amanbutnot.moosic.common.appSettings
import io.github.amanbutnot.moosic.getPlatform
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

object SettingsScreen : Screen {
    @Composable
    override fun Content() {
        SettingsContent()
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsContent() {
    val type = MaterialTheme.typography
    val color = MaterialTheme.colorScheme
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState(),
            snapAnimationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessHigh
            )
        )
    val fraction = scrollBehavior.state.collapsedFraction
    val titleSize = lerp(
        start = type.titleLargeEmphasized.copy(fontSize = 80.sp),
        stop = type.titleSmall,
        fraction = fraction
    )
    val navigator = LocalNavigator.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigator?.pop() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                title = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier,
                            text = "Settings",
                            maxLines = 1,
                            style = titleSize,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                },


                scrollBehavior = scrollBehavior
            )

        },
    )
    { paddingValues ->
        val nav = LocalNavigator.currentOrThrow
        val options = listOf(
            "Change Server",
            "Audio Quality",
            "Appearance",
            "Downloads",
            "Playback",
            "Account Info",
            "Notifications",
            "About Moosic"
        )

        val unCheckedIcons = listOf(
            Icons.Outlined.Dns,
            Icons.Outlined.GraphicEq,
            Icons.Outlined.Palette,
            Icons.Outlined.Download,
            Icons.Outlined.PlayCircle,
            Icons.Outlined.Person,
            Icons.Outlined.Notifications,
            Icons.Outlined.Info
        )

        val checkedIcons = listOf(
            Icons.Filled.Dns,
            Icons.Filled.GraphicEq,
            Icons.Filled.Palette,
            Icons.Filled.Download,
            Icons.Filled.PlayCircle,
            Icons.Filled.Person,
            Icons.Filled.Notifications,
            Icons.Filled.Info
        )

        var selectedIndex by remember { mutableIntStateOf(0) }
        val d = listOf(
            ButtonGroupDefaults.connectedLeadingButtonShapes(),
            ButtonGroupDefaults.connectedTrailingButtonShapes(),
            ButtonGroupDefaults.connectedMiddleButtonShapes()
        )
        val shapesList = remember {
            val random = Random(
                kotlin.time.Clock.System.now().toEpochMilliseconds()
            )
            List(options.size) { d.random(random) }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            itemsIndexed(
                items = options,
                span = { index, _ ->
                    GridItemSpan(if (index % 4 == 0 || index % 4 == 3) 3 else 2)
                }
            ) { index, label ->


                ToggleButton(
                    checked = false,
                    onCheckedChange = {
                        when (index) {
                            0 -> {
                                nav.push(ServerConfigScreen)
                            }
                        }
                    },
                    modifier = Modifier
                        .semantics { role = Role.RadioButton }
                        .height(120.dp),
                    shapes = shapesList[index],
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            if (selectedIndex == index) checkedIcons[index] else unCheckedIcons[index],
                            contentDescription = "Localized description",
                        )
                        Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                        Text(label)

                    }
                }
            }
        }
    }
}

@Composable
fun old() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //  .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 8.dp)
    )
    {
        val serverDesc = if (appSettings.serverUrl.isNotBlank()) {
            "${appSettings.username} @ ${appSettings.serverUrl}"
        } else {
            "Change Server URL and/or current user"
        }
        SettingsItem(
            heading = "Change Server/User",
            description = serverDesc,
            icon = Icons.Default.Settings,
            onClick = {
                //    navigator?.push(ServerConfigScreen)
            }
        )
        SettingsItem(
            heading = "Audio Quality",
            description = "Configure streaming and download quality",
            icon = Icons.Default.Audiotrack,
            onClick = { }
        )
        SettingsItem(
            heading = "Appearance",
            description = "Dark mode and accent colors",
            icon = Icons.Default.Palette,
            onClick = { }
        )
        SettingsItem(
            heading = "Downloads",
            description = "Manage downloaded music and storage",
            icon = Icons.Default.Download,
            onClick = { }
        )
        SettingsItem(
            heading = "Playback",
            description = "Equalizer and gapless playback",
            icon = Icons.Default.PlayArrow,
            onClick = { }
        )
        SettingsItem(
            heading = "Account info",
            description = "View Information about your account",
            icon = Icons.Default.Lock,
            onClick = {
                //    navigator?.push(UserInfoScreen)
            }
        )
        SettingsItem(
            heading = "Notifications",
            description = "Configure alerts and updates",
            icon = Icons.Default.Notifications,
            onClick = { }
        )
        SettingsItem(
            heading = "About Moosic",
            description = "Version information and licenses",
            icon = Icons.Default.Info,
            onClick = { }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Moosic",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Version 1.0.0 (${getPlatform().name})",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun SettingsItem(
    heading: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = heading,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}
