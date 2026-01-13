package io.github.amanbutnot.moosic.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import io.github.amanbutnot.moosic.business.AlbumSongsViewModel

data class AlbumDetailScreen(val id: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: AlbumSongsViewModel = viewModel { AlbumSongsViewModel(id) }
        val state by viewModel.state

        if (state.data != null) {
            Text(state.data.toString())

        }
    }
}
