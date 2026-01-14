package io.github.amanbutnot.moosic.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

actual class SongPlayer2 actual constructor(songURL: String) {
    actual fun play() {}
    actual fun pause() {}
    actual fun stop() {}
    actual fun seekTo(position: Long) {}
    actual fun setVolume(volume: Float) {}
    actual fun release() {}
    actual fun getCurrentPosition(): Long {
        return 0L
    }

    actual fun getDuration(): Long {
        return 0L

    }
}

@Composable
actual fun rememberSongPlayer(songURL: String): SongPlayer2 {
    val player = remember(songURL) {
        SongPlayer2(songURL)
    }
    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }
    return player
}
