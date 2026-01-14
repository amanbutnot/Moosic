package io.github.amanbutnot.moosic.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import io.github.amanbutnot.moosic.common.SongPlayer

object SongPlayerScreen: Screen{
    @Composable
    override fun Content() {
        SongPlayer("https://music.hoelab.org/rest/stream.view?u=test&p=narayan7&v=1.16.1&c=feagegsag&f=json&id=UjxJkuj9pj2qZkjRc15uHD")
    }
}