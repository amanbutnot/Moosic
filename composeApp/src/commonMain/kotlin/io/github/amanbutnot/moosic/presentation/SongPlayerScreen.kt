package io.github.amanbutnot.moosic.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import io.github.amanbutnot.moosic.common.rememberSongPlayer
import io.github.amanbutnot.moosic.data.constants.getImage
import io.github.amanbutnot.moosic.data.model.Song
import kotlinx.coroutines.delay

data class SongPlayerScreen(val song: Song) : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val url = "https://music.hoelab.org/rest/stream.view?u=test&p=narayan7&v=1.16.1&c=feagegsag&f=json&id=${song.id}"
        
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            // Background Image
            AsyncImage(
                model = getImage(song.coverArt),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.6f },
                contentScale = ContentScale.Crop
            )

            // Scrim / Gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.9f)
                            ),
                            startY = 300f
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { nav.pop() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Text(
                        "Playing Your Mix",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    IconButton(onClick = { /* Add to playlist */ }) {
                        Icon(Icons.AutoMirrored.Filled.PlaylistAdd, contentDescription = "Playlist", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Song Title & Artist
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = song.title,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = (-1).sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = song.artist,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        )
                    }
                    
                    var isFavorite by remember { mutableStateOf(false) }
                    Surface(
                        onClick = { isFavorite = !isFavorite },
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color.Red else Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Wavy Progress Area
                SongProgress(url)

                Spacer(modifier = Modifier.height(40.dp))

                // Playback Controls (Expressive Layout)
                PlaybackControls(url)

                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    private fun SongProgress(url: String) {
        val player = rememberSongPlayer(url)
        var currentPosition by remember { mutableLongStateOf(0L) }
        var duration by remember { mutableLongStateOf(0L) }

        LaunchedEffect(Unit) {
            while (true) {
                currentPosition = player.getCurrentPosition()
                duration = player.getDuration()
                delay(500)
            }
        }

        Box(modifier = Modifier.fillMaxWidth().height(48.dp), contentAlignment = Alignment.Center) {
            LinearWavyProgressIndicator(
                progress = { if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = Color(0xFF8AB4F8),
                trackColor = Color.White.copy(alpha = 0.2f),
                wavelength = 24.dp,
                amplitude = { 3f }
            )
            
            // Interaction Slider
            Slider(
                value = if (duration > 0) currentPosition.toFloat() else 0f,
                onValueChange = { 
                    currentPosition = it.toLong()
                    player.seekTo(it.toLong())
                },
                valueRange = 0f..(if (duration > 0) duration.toFloat() else 1f),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF8AB4F8),
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    private fun PlaybackControls(url: String) {
        val player = rememberSongPlayer(url)
        var isPlaying by remember { mutableStateOf(false) }
        
        Row(
            modifier = Modifier.fillMaxWidth().height(140.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Play/Pause Large Circle
            Surface(
                onClick = {
                    if (isPlaying) player.pause() else player.play()
                    isPlaying = !isPlaying
                },
                modifier = Modifier.size(140.dp),
                shape = CircleShape,
                color = Color(0xFFADC6FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        modifier = Modifier.size(56.dp),
                        tint = Color.Black
                    )
                }
            }

            // Prev Pill
            Surface(
                onClick = { /* Prev */ },
                modifier = Modifier.weight(1f).fillMaxHeight(),
                shape = RoundedCornerShape(70.dp),
                color = Color(0xFF4DB6E5)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = "Prev", tint = Color.Black, modifier = Modifier.size(32.dp))
                }
            }

            // Next Pill
            Surface(
                onClick = { /* Next */ },
                modifier = Modifier.weight(1f).fillMaxHeight(),
                shape = RoundedCornerShape(70.dp),
                color = Color(0xFF4DB6E5)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = Color.Black, modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}

private fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes}:${seconds.toString().padStart(2, '0')}"
}
