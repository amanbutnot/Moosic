package io.github.amanbutnot.moosic.common

import androidx.compose.runtime.Composable

expect class SongPlayer2(songURL: String) {
    fun play()
    fun pause()
    fun stop()
    fun seekTo(position: Long)
    fun setVolume(volume: Float)
    fun release()
    fun getCurrentPosition(): Long
    fun getDuration(): Long
}

@Composable
expect fun rememberSongPlayer(songURL: String): SongPlayer2
