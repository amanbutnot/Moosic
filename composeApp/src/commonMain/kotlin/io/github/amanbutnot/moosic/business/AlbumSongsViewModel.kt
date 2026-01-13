package io.github.amanbutnot.moosic.business

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.amanbutnot.moosic.data.model.AlbumSongsResponse
import io.github.amanbutnot.moosic.network.getAlbumSongs
import kotlinx.coroutines.launch

class AlbumSongsViewModel(id: String): ViewModel() {
    private val _state = mutableStateOf(DataState())
    val state: MutableState<DataState> = _state

    data class DataState(
        val success: Boolean = false,
        val loading: Boolean = false,
        val data: AlbumSongsResponse? = null,
        val message: String? = null,
    )

    init {
        getDataState(id)
    }

    fun getDataState(
        id:String
    ) {
        viewModelScope.launch {
            _state.value = DataState(loading = true)
            try {
                val albumSongResponse = getAlbumSongs(id)
                _state.value = DataState(
                    success = true,
                    loading = false,
                    data = albumSongResponse,
                    message = "Album List Fetched Successfully"
                )
            } catch (e: Exception) {
                _state.value = DataState(
                    loading = false,
                    success = false,
                    data = null,
                    message = e.message,
                )
            }
        }
    }
}

