package io.github.amanbutnot.moosic.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.github.amanbutnot.moosic.common.rememberSongPlayer
import kotlinx.coroutines.delay

object SongPlayerScreen : Screen {
    @Composable
    override fun Content() {
        val songUrl =
            "https://music.hoelab.org/rest/stream.view?u=test&p=narayan7&v=1.16.1&c=feagegsag&f=json&id=UjxJkuj9pj2qZkjRc15uHD"

        val songPlayer = rememberSongPlayer(songUrl)
        var isPlaying by remember { mutableStateOf(false) }
        var currentPosition by remember { mutableLongStateOf(0L) }
        var totalDuration by remember { mutableLongStateOf(0L) }

        LaunchedEffect(isPlaying) {
            while (isPlaying) {
                currentPosition = songPlayer.getCurrentPosition()
                totalDuration = songPlayer.getDuration()
                delay(1000)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Slider(
                value = currentPosition.toFloat(),
                onValueChange = {
                    currentPosition = it.toLong()
                    songPlayer.seekTo(currentPosition)
                },
                valueRange = 0f..(if (totalDuration > 0) totalDuration.toFloat() else 1f),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    val currentPos = songPlayer.getCurrentPosition()
                    songPlayer.seekTo((currentPos - 10000).coerceAtLeast(0))
                }) {
                    Icon(
                        imageVector = Icons.Default.Replay10,
                        contentDescription = "Rewind 10s"
                    )
                }

                IconButton(onClick = {
                    if (isPlaying) {
                        songPlayer.pause()
                    } else {
                        songPlayer.play()
                    }
                    isPlaying = !isPlaying
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }

                IconButton(onClick = {
                    val currentPos = songPlayer.getCurrentPosition()
                    songPlayer.seekTo(currentPos + 10000)
                }) {
                    Icon(
                        imageVector = Icons.Default.Forward10,
                        contentDescription = "Forward 10s"
                    )
                }
            }

            IconButton(onClick = {
                songPlayer.seekTo(0)
                currentPosition = 0
            }) {
                Icon(
                    imageVector = Icons.Default.Replay,
                    contentDescription = "Reset"
                )
            }
        }
    }
}
