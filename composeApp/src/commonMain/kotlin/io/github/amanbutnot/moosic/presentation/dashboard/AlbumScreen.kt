package io.github.amanbutnot.moosic.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.QueueMusic
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.kmpalette.rememberDominantColorState
import io.github.amanbutnot.moosic.business.AlbumListViewModel
import io.github.amanbutnot.moosic.data.model.AlbumModel
import moosic.composeapp.generated.resources.Res
import moosic.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


object AlbumScreen : Screen {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    override fun Content() {
        AlbumTab()
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
private fun AlbumTab() {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        val options = listOf("Songs", "Albums", "Artist", "Playlist", "Folder", "Liked")
        val viewModel: AlbumListViewModel = viewModel { AlbumListViewModel(size = 100) }
        val state by viewModel.state

        val unCheckedIcons = listOf(
            Icons.Outlined.MusicNote,      // Songs
            Icons.Outlined.Album,          // Albums
            Icons.Outlined.Person,         // Artist
            Icons.AutoMirrored.Outlined.QueueMusic,     // Playlist
            Icons.Outlined.Folder,         // Folder
            Icons.Outlined.FavoriteBorder  // Liked
        )

        val checkedIcons = listOf(
            Icons.Filled.MusicNote,        // Songs
            Icons.Filled.Album,            // Albums
            Icons.Filled.Person,           // Artist
            Icons.AutoMirrored.Outlined.QueueMusic,       // Playlist
            Icons.Filled.Folder,           // Folder
            Icons.Filled.Favorite          // Liked
        )

        var selectedIndex by remember { mutableIntStateOf(1) }

        Row(
            Modifier.padding(vertical = 16.dp).fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            Spacer(Modifier.size(4.dp))
            options.forEachIndexed { index, label ->
                ToggleButton(
                    checked = selectedIndex == index,
                    onCheckedChange = { selectedIndex = index },
                    shapes = when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                ) {
                    Icon(
                        if (selectedIndex == index) checkedIcons[index] else unCheckedIcons[index],
                        contentDescription = label,
                    )
                    Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                    Text(label, style = MaterialTheme.typography.labelLarge)
                }
            }
            Spacer(Modifier.size(4.dp))
        }

        if (state.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingIndicator()
            }
        } else if (!state.success || state.data == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.message ?: "Error Occurred")
            }
        } else {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )
            {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.data!!.subsonicResponse.albumList.album) {
                        AlbumCard(it)
                    }
                }
            }
        }


    }
}

@Composable
private fun AlbumCard(model: AlbumModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val dominantColorState = rememberDominantColorState()
        val pain: Painter = painterResource(Res.drawable.compose_multiplatform)
        LaunchedEffect(Res.drawable.compose_multiplatform) {
            dominantColorState.updateFrom(painterToImageBitmap(pain, size = IntSize(200, 200)))
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = dominantColorState.color.copy(alpha = 0.6f))
        ) {
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = model.name.toString(),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Text(
            text = model.artist.toString(),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}


fun painterToImageBitmap(
    painter: Painter,
    size: IntSize,
    density: Density = Density(1f)
): ImageBitmap {
    val imageBitmap = ImageBitmap(
        width = size.width,
        height = size.height,
        config = ImageBitmapConfig.Argb8888
    )

    val canvas = Canvas(imageBitmap)
    val drawScope = CanvasDrawScope()

    drawScope.draw(
        density = density,
        layoutDirection = LayoutDirection.Ltr,
        canvas = canvas,
        size = Size(size.width.toFloat(), size.height.toFloat())
    ) {
        with(painter) {
            draw(size = this@draw.size)
        }
    }

    return imageBitmap
}
