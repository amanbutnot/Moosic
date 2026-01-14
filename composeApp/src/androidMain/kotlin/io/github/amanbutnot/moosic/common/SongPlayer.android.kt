package io.github.amanbutnot.moosic.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

actual class SongPlayer2 actual constructor(songURL: String) {
    private var exoPlayer: ExoPlayer? = null
    private val url = songURL

    fun init(context: android.content.Context) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(url)
                setMediaItem(mediaItem)
                prepare()
            }
        }
    }

    actual fun play() {
        exoPlayer?.play()
    }

    actual fun pause() {
        exoPlayer?.pause()
    }

    actual fun stop() {
        exoPlayer?.stop()
    }

    actual fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
    }

    actual fun setVolume(volume: Float) {
        exoPlayer?.volume = volume
    }

    actual fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }

    actual fun getCurrentPosition(): Long {
        return exoPlayer?.currentPosition ?: 0L
    }

    actual fun getDuration(): Long {
        val duration = exoPlayer?.duration ?: 0L
        return if (duration < 0) 0L else duration
    }
}

@Composable
actual fun rememberSongPlayer(songURL: String): SongPlayer2 {
    val context = LocalContext.current
    val player = remember(songURL) {
        SongPlayer2(songURL).apply {
            init(context)
        }
    }
    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }
    return player
}
