package io.github.amanbutnot.moosic.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.*
import platform.Foundation.*
import platform.CoreMedia.*

/**
 * iOS implementation of the [SongPlayer2] using [AVPlayer].
 *
 * @param songURL The URL of the song to be played.
 */
actual class SongPlayer2 actual constructor(songURL: String) {
    private val player: AVPlayer = AVPlayer.playerWithURL(NSURL.URLWithString(songURL)!!)

    /**
     * Resumes or starts the playback of the current song.
     */
    actual fun play() {
        player.play()
    }

    /**
     * Pauses the playback of the current song.
     */
    actual fun pause() {
        player.pause()
    }

    /**
     * Stops the playback and resets the song position to the beginning.
     */
    @OptIn(ExperimentalForeignApi::class)
    actual fun stop() {
        player.pause()
        player.seekToTime(CMTimeMake(0, 1))
    }

    /**
     * Seeks to a specific position in the song.
     *
     * @param position The position in milliseconds to seek to.
     */
    @OptIn(ExperimentalForeignApi::class)
    actual fun seekTo(position: Long) {
        val time = CMTimeMakeWithSeconds(position.toDouble() / 1000.0, 1000)
        player.seekToTime(time)
    }

    /**
     * Sets the volume of the audio playback.
     *
     * @param volume The volume level, usually between 0.0 and 1.0.
     */
    actual fun setVolume(volume: Float) {
        player.volume = volume
    }

    actual fun release() {
        player.pause()
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun getCurrentPosition(): Long {
        return (CMTimeGetSeconds(player.currentTime()) * 1000).toLong()
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun getDuration(): Long {
        val duration = player.currentItem?.duration ?: return 0L
        return (CMTimeGetSeconds(duration) * 1000).toLong()
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
