package io.github.amanbutnot.moosic.business

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.amanbutnot.moosic.data.model.AlbumResponse
import io.github.amanbutnot.moosic.network.getAlbumList
import kotlinx.coroutines.launch

class AlbumViewModel() : ViewModel() {
    private val _state = mutableStateOf(DataState())
    val state: MutableState<DataState> = _state

    data class DataState(
        val success: Boolean = false,
        val loading: Boolean = false,
        val data: AlbumResponse? = null,
        val message: String? = null,
    )

    fun getDataState() {
        viewModelScope.launch {
            _state.value = DataState(loading =true )
            try {
                val albumResponse = getAlbumList()
                _state.value = DataState(
                    success = true,
                    loading = true,
                    data = albumResponse,
                    message = "Album List Fetched Successfully"
                )
            }catch (e: Exception){
                _state.value= DataState(
                    loading = false,
                    success = false,
                    data = null,
                    message = e.message,
                )
            }
        }
    }
}

